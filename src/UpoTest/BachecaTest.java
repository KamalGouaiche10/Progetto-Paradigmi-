package UpoTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import upoBacheca.Annuncio;
import upoBacheca.AnnuncioAcquisto;
import upoBacheca.AnnuncioVendita;
import upoBacheca.Bacheca;
import upoBacheca.BachecaImplementa;
import upoBacheca.Utente;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Classe di test per verificare il corretto funzionamento della classe Bacheca.
 * @author Gouaiche, Diabi
 */
class BachecaTest {
	
    private Bacheca bacheca;
    private Utente utente;
    private ArrayList<String> paroleChiave;
    private AnnuncioVendita annuncioVendita;
    private AnnuncioAcquisto annuncioAcquisto;

    /**
     * Inizializza l'ambiente di test prima di ogni metodo.
     * Crea bacheca, utente e annunci di test.
     */
    @BeforeEach
    void setUp() {
       
        bacheca = new BachecaImplementa();
        utente = new Utente("Mario", "Rossi", "mario.rossi@email.com");
        paroleChiave = new ArrayList<>();
        paroleChiave.add("elettronica");
        paroleChiave.add("tecnologia");
        LocalDate dataScadenza = LocalDate.now().plusDays(7);
        annuncioVendita = new AnnuncioVendita(1, "iPhone", 800.0, paroleChiave, utente, dataScadenza);
        annuncioAcquisto = new AnnuncioAcquisto(2, "MacBook", 1200.0, paroleChiave, utente);
    }
    
    
    /**
     * Verifica aggiunta annunci alla bacheca.
     * Assert verificati:
     * - assertTrue(hasNext): verifica presenza annuncio
     * - assertEquals: verifica correttezza annuncio inserito
     * - assertThrows: verifica gestione annuncio null
     */
    /**
     * Verifica che un annuncio venga aggiunto correttamente alla bacheca.
     * Assert verificati:
     * - assertTrue(hasNext): verifica che l'annuncio sia presente nella bacheca.
     * - assertEquals: verifica che l'annuncio inserito sia corretto.
     * - assertThrows: verifica che venga sollevata un'eccezione se si tenta di aggiungere un annuncio null.
     */
    @Test
    void testAggiungiAnnuncio() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        Iterator<Annuncio> it = bacheca.iteratore();
        assertTrue(it.hasNext());
        assertEquals(annuncioVendita, it.next());
        assertThrows(IllegalArgumentException.class, () -> bacheca.aggiungiAnnuncio(null));
    }

    /**
     * Verifica che un annuncio venga rimosso correttamente dalla bacheca.
     * Assert verificati:
     * - assertFalse(hasNext): verifica che l'annuncio sia stato rimosso.
     * - assertThrows per utente null: verifica che venga sollevata un'eccezione se l'utente è null.
     * - assertThrows per ID invalido: verifica che venga sollevata un'eccezione se l'ID non esiste.
     * - assertThrows per utente non autorizzato: verifica che venga sollevata un'eccezione se l'utente non è il creatore.
     */
    @Test
    void testRemoveAnnuncio() {
        bacheca.aggiungiAnnuncio(annuncioVendita);
        bacheca.removeAnnuncio(utente, annuncioVendita.getId());
        Iterator<Annuncio> it = bacheca.iteratore();
        assertFalse(it.hasNext());

        assertThrows(IllegalArgumentException.class, () -> bacheca.removeAnnuncio(null, annuncioVendita.getId()));
        assertThrows(IllegalArgumentException.class, () -> bacheca.removeAnnuncio(utente, 999));
        Utente altroUtente = new Utente("Luigi", "Verdi", "luigi.verdi@email.com");
        bacheca.aggiungiAnnuncio(annuncioVendita);
        assertThrows(IllegalArgumentException.class, () -> bacheca.removeAnnuncio(altroUtente, annuncioVendita.getId()));
    }

    /**
     * Verifica ricerca annunci per parola chiave.
     * Assert verificati:
     * - assertFalse(isEmpty): verifica presenza risultati
     * - assertEquals size: verifica numero risultati corretto
     * - assertTrue(isEmpty): verifica assenza risultati per chiave inesistente
     * - assertThrows: verifica gestione input non validi
     */
    @Test
    void testCercaAnnuncioPerParolaChiave() {
       
        bacheca.aggiungiAnnuncio(annuncioVendita);
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        
        ArrayList<Annuncio> risultati = bacheca.CercaAnnunncioPerParolaChiave("elettronica");
        assertFalse(risultati.isEmpty());
        assertEquals(2, risultati.size());
        
        risultati = bacheca.CercaAnnunncioPerParolaChiave("elettronica, tecnologia");
        assertEquals(2, risultati.size());
        
        risultati = bacheca.CercaAnnunncioPerParolaChiave("inesistente");
        assertTrue(risultati.isEmpty());
        assertThrows(IllegalArgumentException.class, () -> {bacheca.CercaAnnunncioPerParolaChiave(null);});
        assertThrows(IllegalArgumentException.class, () -> {bacheca.CercaAnnunncioPerParolaChiave("");});
    }

    /**
     * Verifica pulizia completa bacheca.
     * Assert verificati:
     * - assertEquals count: verifica numero annunci
     * - assertThrows: verifica gestione date non valide
     */
    @Test
    void testPulisciBacheca() {
       
        LocalDate futuro = LocalDate.now().plusDays(7);  
        LocalDate futuroLontano = LocalDate.now().plusDays(30); 
        AnnuncioVendita annuncioValido1 = new AnnuncioVendita(3, "TV", 500.0, paroleChiave, utente, futuro);
        AnnuncioVendita annuncioValido2 = new AnnuncioVendita(4, "Radio", 100.0, paroleChiave, utente, futuroLontano);
        LocalDate passato = LocalDate.now().minusDays(1);
        assertThrows(IllegalArgumentException.class, () -> {new AnnuncioVendita(5, "DVD", 50.0, paroleChiave, utente, passato);});
        
        bacheca.aggiungiAnnuncio(annuncioValido1);
        bacheca.aggiungiAnnuncio(annuncioValido2);
        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        Iterator<Annuncio> it = bacheca.iteratore();
        int initialCount = 0;
        while (it.hasNext()) {
            it.next();
            initialCount++;
        }
        assertEquals(3, initialCount);
        
        bacheca.pulisciBacheca();
     
        it = bacheca.iteratore();
        int countAfterClean = 0;
        while (it.hasNext()) {
            it.next();
            countAfterClean++;
        }
        assertEquals(3, countAfterClean);
    }
    
    /**
    * Test del metodo getAnnunciOrdinatiPerPrezzo della classe BachecaImplementa.
    * 
    * Gli assert verificano che:
    * - il primo annuncio nella lista ordinata abbia prezzo 50.0
    * - il secondo annuncio nella lista ordinata abbia prezzo 100.0  
    * - il terzo annuncio nella lista ordinata abbia prezzo 200.0
    */
    @Test
    void testGetAnnunciOrdinatiPerPrezzo() {
        Annuncio annuncio1 = new AnnuncioVendita(1, "Articolo1", 100.0, paroleChiave, utente, LocalDate.now().plusDays(10));
        Annuncio annuncio2 = new AnnuncioVendita(2, "Articolo2", 50.0, paroleChiave, utente, LocalDate.now().plusDays(10));
        Annuncio annuncio3 = new AnnuncioVendita(3, "Articolo3", 200.0, paroleChiave, utente, LocalDate.now().plusDays(10));
 
        bacheca.aggiungiAnnuncio(annuncio1);
        bacheca.aggiungiAnnuncio(annuncio2);
        bacheca.aggiungiAnnuncio(annuncio3);

        ArrayList<Annuncio> annunciOrdinati = ((BachecaImplementa) bacheca).getAnnunciOrdinatiPerPrezzo();

        assertEquals(50.0, annunciOrdinati.get(0).getPrezzo(), "Il primo annuncio deve essere quello con prezzo 50");
        assertEquals(100.0, annunciOrdinati.get(1).getPrezzo(), "Il secondo annuncio deve essere quello con prezzo 100");
        assertEquals(200.0, annunciOrdinati.get(2).getPrezzo(), "Il terzo annuncio deve essere quello con prezzo 200");
    }

    /**
     * Verifica aggiunta parola chiave a un annuncio.
     * Assert verificati:
     * - assertThrows utente non autorizzato: verifica autorizzazioni
     * - assertEquals size: verifica aggiunta effettiva
     * - assertThrows input non validi: verifica validazione input
     */
    @Test
    void testAggiungiParolaChiave() {
      
        ArrayList<String> paroleChiave = new ArrayList<>();
        paroleChiave.add("mobile");
        
        Utente altroUtente = new Utente("Luigi", "Verdi", "luigi.verdi@email.com");
       
        AnnuncioVendita annuncio = new AnnuncioVendita(0, "Tablet", 200.0, paroleChiave, utente, LocalDate.now().plusDays(15));
        bacheca.aggiungiAnnuncio(annuncio);
        int id = annuncio.getId();
        assertThrows(IllegalArgumentException.class, () -> {bacheca.aggiungiParolaChiave(altroUtente, id, "nuovo");});

        bacheca.aggiungiParolaChiave(utente, id, "nuovo");
        ArrayList<Annuncio> risultati = bacheca.CercaAnnunncioPerParolaChiave("nuovo");
        assertEquals(1, risultati.size()); 
        assertThrows(IllegalArgumentException.class, () -> {bacheca.aggiungiParolaChiave(utente, id, null);});
        assertThrows(IllegalArgumentException.class, () -> {bacheca.aggiungiParolaChiave(utente, id, "");});
    }
    
    
    /**
     * Verifica la rimozione di parole chiave da un annuncio.
     * Assert verificati:
     * - assertFalse(contains): verifica rimozione effettiva parola chiave
     * - assertThrows utente non autorizzato: verifica permessi di modifica
     * - assertThrows parola chiave null: verifica validazione input null
     * - assertThrows parola chiave vuota: verifica validazione stringa vuota
     * - assertThrows ID invalido: verifica gestione ID inesistente
     */
    @Test
    void testRimuoviParolaChiave() {
        
        BachecaImplementa bacheca = new BachecaImplementa();
        Utente utenteAutore = new Utente("Mario", "Rossi", "mario.rossi@email.com");
        Utente utenteNonAutore = new Utente("Luigi", "Verdi", "luigi.verdi@email.com");

     
        ArrayList<String> paroleChiave = new ArrayList<>();
        paroleChiave.add("auto");
        paroleChiave.add("vendita");

      
        AnnuncioVendita annuncio = new AnnuncioVendita(1, "Vendita auto usata", 5000.0, paroleChiave, utenteAutore, LocalDate.now().plusDays(7));
        bacheca.aggiungiAnnuncio(annuncio);

      
        int idAnnuncio = annuncio.getId(); 

   
        bacheca.rimuoviParolaChiave(utenteAutore, idAnnuncio, "auto");
        assertFalse(annuncio.getParoleChiave().contains("auto"), "La parola chiave 'auto' dovrebbe essere stata rimossa");
        assertThrows(IllegalArgumentException.class, () -> {bacheca.rimuoviParolaChiave(utenteNonAutore, idAnnuncio, "vendita");}, "Dovrebbe essere lanciata un'eccezione per utente non autorizzato");
        assertThrows(IllegalArgumentException.class, () -> {bacheca.rimuoviParolaChiave(utenteAutore, idAnnuncio, null);}, "Dovrebbe essere lanciata un'eccezione per parola chiave nulla");
        assertThrows(IllegalArgumentException.class, () -> {bacheca.rimuoviParolaChiave(utenteAutore, idAnnuncio, "");}, "Dovrebbe essere lanciata un'eccezione per parola chiave vuota");
        assertThrows(IllegalArgumentException.class, () -> {bacheca.rimuoviParolaChiave(utenteAutore, 999, "auto");}, "Dovrebbe essere lanciata un'eccezione per annuncio non trovato");
    }

    /**
     * Verifica funzionamento dell'iteratore della bacheca.
     * Assert verificati:
     * - assertFalse(hasNext): verifica bacheca vuota
     * - assertTrue(hasNext): verifica presenza elementi
     * - assertNotNull(next): verifica recupero elementi
     * - assertFalse(hasNext) finale: verifica fine iterazione
     */
    @Test
    void testIteratore() {
        Iterator<Annuncio> it = bacheca.iteratore();
        assertFalse(it.hasNext());
        bacheca.aggiungiAnnuncio(annuncioVendita);

        bacheca.aggiungiAnnuncio(annuncioAcquisto);
        it = bacheca.iteratore();
        assertTrue(it.hasNext());
        assertNotNull(it.next());
        assertTrue(it.hasNext());
        assertNotNull(it.next());
        assertFalse(it.hasNext());

    }

}