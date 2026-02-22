package UpoTest;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import upoBacheca.AnnuncioAcquisto;
import upoBacheca.AnnuncioVendita;
import upoBacheca.Utente;

/**
 * Classe di test per verificare il corretto funzionamento della classe Annuncio 
 * e delle sue sottoclassi AnnuncioVendita e AnnuncioAcquisto.
 * 
 * @author Gouaiche, Diabi
 */
class AnnuncioTest {
    
    private Utente creatore;
    private ArrayList<String> paroleChiave;

    /**
     * Inizializza gli oggetti necessari per i test prima di ogni test.
     * - Crea un utente di test con dati predefiniti
     * - Inizializza una lista di parole chiave con due elementi
     */
    @BeforeEach
    void setUp() {
        creatore = new Utente("Nome Utente", "Cognome Utente", "email@example.com");
        paroleChiave = new ArrayList<>();
        paroleChiave.add("keyword1");
        paroleChiave.add("keyword2");
    }

    /**
     * Verifica che un annuncio di vendita venga creato correttamente con i dati forniti.
     * Assert verificati:
     * - assertEquals("Articolo1", ...): verifica che il nome dell'articolo sia corretto.
     * - assertEquals(100.0, ...): verifica che il prezzo sia corretto.
     * - assertEquals(creatore, ...): verifica che l'utente creatore sia associato correttamente.
     * - assertEquals(dataScadenza, ...): verifica che la data di scadenza sia corretta.
     * - assertFalse(IsScaduto()): verifica che l'annuncio non sia scaduto.
     */
    @Test
    void testAnnuncioVenditaValido() {
        LocalDate dataScadenza = LocalDate.now().plusDays(1);
        AnnuncioVendita annuncioVendita = new AnnuncioVendita(1, "Articolo1", 100.0, paroleChiave, creatore, dataScadenza);
        assertEquals("Articolo1", annuncioVendita.getArticolo());
        assertEquals(100.0, annuncioVendita.getPrezzo());
        assertEquals(creatore, annuncioVendita.getCreatore());
        assertEquals(dataScadenza, annuncioVendita.getDataScadenza());
        assertFalse(annuncioVendita.IsScaduto());
    }

    /**
     * Verifica che venga sollevata un'eccezione se si tenta di creare un annuncio di vendita con una data di scadenza nel passato.
     * Assert verificati:
     * - assertThrows: verifica che venga sollevata un'eccezione IllegalArgumentException.
     * - assertEquals sul messaggio: verifica che il messaggio di errore sia corretto.
     */
    @Test
    void testAnnuncioVenditaScaduto() {
        LocalDate dataScadenza = LocalDate.now().minusDays(1);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new AnnuncioVendita(1, "ArticoloScaduto", 100.0, paroleChiave, creatore, dataScadenza));
        assertEquals("data di scadenza errata, non può essere nel passato", thrown.getMessage());
    }
    /**
     * Verifica la corretta aggiunta di una nuova parola chiave.
     * Assert verificato:
     * - assertTrue(contains("newKeyword")): verifica presenza nuova parola
     */
    @Test
    void testAggiungiParolaChiave() {
        AnnuncioVendita annuncioVendita = new AnnuncioVendita(1, "Articolo1", 100.0, paroleChiave, creatore, LocalDate.now().plusDays(1));
        annuncioVendita.aggiungiParolaChiave("newKeyword");
        assertTrue(annuncioVendita.getParoleChiave().contains("newKeyword"));
    }

    /**
     * Verifica la corretta rimozione di una parola chiave.
     * Assert verificato:
     * - assertFalse(contains("keyword1")): verifica rimozione parola
     */
    @Test
    void testEliminaParolaChiave() {
        AnnuncioVendita annuncioVendita = new AnnuncioVendita(1, "Articolo1", 100.0, paroleChiave, creatore, LocalDate.now().plusDays(1));
        annuncioVendita.eliminaParolaChiave("keyword1");
        assertFalse(annuncioVendita.getParoleChiave().contains("keyword1"));
    }

    /**
     * Verifica che venga lanciata un'eccezione per prezzo negativo.
     * Assert verificati:
     * - assertThrows: verifica eccezione per prezzo negativo
     * - assertEquals sul messaggio: verifica messaggio errore corretto
     */
    @Test
    void testPrezzoNegativo() {
        LocalDate dataScadenza = LocalDate.now().plusDays(30);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,() -> new AnnuncioVendita(1, "Articolo", -50.0, paroleChiave, creatore, dataScadenza));
        assertEquals("Il prezzo deve essere maggiore da 0", thrown.getMessage());
    }


    /**
     * Suite di test interni per funzionalità specifiche degli annunci.
     */
    class AnnuncioTestSuite {
        private AnnuncioVendita annuncioVendita;
        private AnnuncioAcquisto annuncioAcquisto;
        private Utente creatore;
        private ArrayList<String> paroleChiave;

        /**
         * Inizializza l'ambiente di test con dati predefiniti.
         * Crea annunci di test sia di vendita che di acquisto.
         */
        @BeforeEach
        void setUp() {
            creatore = new Utente("Mohammed", "al-farisi", "mario.rossi@example.com");
            paroleChiave = new ArrayList<>();
            paroleChiave.add("elettronica");
            paroleChiave.add("nuovo");
            annuncioVendita = new AnnuncioVendita(0, "PC portabile", 650.00, paroleChiave, creatore, LocalDate.now().plusDays(10));
            annuncioAcquisto = new AnnuncioAcquisto(0, "Cellulare", 749.99, paroleChiave, creatore);
        }

        /**
         * Verifica inizializzazione AnnuncioVendita.
         * Assert verificati:
         * - assertEquals articolo: verifica nome articolo
         * - assertEquals prezzo: verifica prezzo
         * - assertEquals parole chiave: verifica numero parole chiave
         * - assertFalse scaduto: verifica stato non scaduto
         */
        @Test
        void testAnnuncioVenditaInizializzazione() {
            assertEquals("Laptop", annuncioVendita.getArticolo(), "L'articolo deve corrispondere a 'Laptop'");
            assertEquals(1000.0, annuncioVendita.getPrezzo(), "Il prezzo deve corrispondere a 1000.0");
            assertEquals(2, annuncioVendita.getParoleChiave().size(), "Il numero di parole chiave iniziali deve essere 2");
            assertFalse(annuncioVendita.IsScaduto(), "L'annuncio non dovrebbe essere scaduto");
        }
        

        /**
         * Verifica controllo scadenza annuncio vendita.
         * Assert verificato:
         * - assertTrue scaduto: verifica rilevamento scadenza
         */
        @Test
        void testAnnuncioVenditaScaduto() {
            AnnuncioVendita annuncioScaduto = new AnnuncioVendita(0, "Tablet", 300.0, paroleChiave, creatore, LocalDate.now().minusDays(1));
            assertTrue(annuncioScaduto.IsScaduto(), "L'annuncio dovrebbe essere scaduto");
        }
        /**
         * Verifica gestione data scadenza non valida.
         * Assert verificato:
         * - assertThrows: verifica eccezione per data passata
         */
        @Test
        void testAnnuncioVenditaDataScadenzaNonValida() {
            assertThrows(IllegalArgumentException.class,() -> {new AnnuncioVendita(0, "Monitor", 150.0, paroleChiave, creatore, LocalDate.now().minusDays(5));},"La data di scadenza non può essere nel passato");
        }

        /**
         * Verifica toString di AnnuncioVendita.
         * Assert verificato:
         * - assertEquals: verifica formato stringa corretto
         */
        @Test
        void testAnnuncioVenditaToString() {
            String expected = "AnnuncioVendita [dataScadenza=" + annuncioVendita.getDataScadenza() + "]";
            assertEquals(expected, annuncioVendita.toString(), "Il metodo toString non restituisce il valore atteso");
        }
        /**
         * Verifica la corretta inizializzazione di un AnnuncioAcquisto.
         * Controlla che tutti i campi siano stati impostati correttamente
         * e che l'annuncio non sia mai considerato scaduto.
         */
        @Test
        void testAnnuncioAcquistoInizializzazione() {
            assertEquals("Smartphone", annuncioAcquisto.getArticolo(), "L'articolo deve corrispondere a 'Smartphone'");
            assertEquals(500.0, annuncioAcquisto.getPrezzo(), "Il prezzo deve corrispondere a 500.0");
            assertEquals(2, annuncioAcquisto.getParoleChiave().size(), "Il numero di parole chiave iniziali deve essere 2");
            assertFalse(annuncioAcquisto.IsScaduto(), "L'annuncio di acquisto non dovrebbe mai essere scaduto");
        }

        /**
         * Verifica la corretta formattazione del metodo toString()
         * per la classe AnnuncioAcquisto.
         */
        @Test
        void testAnnuncioAcquistoToString() {
            String expected = annuncioAcquisto.toString();
            assertTrue(expected.contains("( Annuncio di acquisto)"), "Il metodo toString dovrebbe indicare che è un annuncio di acquisto");
        }

        /**
         * Verifica la corretta formattazione del metodo toString()
         * per la classe base Annuncio.
         */
        @Test
        void testToString() {
            AnnuncioVendita annuncioVendita = new AnnuncioVendita(1, "Articolo1", 100.0, paroleChiave, creatore, LocalDate.now().plusDays(1));
            String expected = "Annuncio [id=1, articolo=Articolo1, prezzo=100.0, paroleChiave=[keyword1, keyword2], creatore=" + creatore + "]";
            assertEquals(expected, annuncioVendita.toString());
        }
    }
}