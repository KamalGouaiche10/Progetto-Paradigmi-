package UpoTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import upoBacheca.Utente;
import java.util.ArrayList;

/**
 * Classe di test per verificare il corretto funzionamento della classe Utente.
 * 
 * @author Gouaiche, Diabi
 */
class UtenteTest {

	/**
	 * Verifica che un utente venga creato correttamente con i dati forniti.
	 * Assert verificati:
	 * - assertEquals nome: verifica che il nome sia corretto.
	 * - assertEquals cognome: verifica che il cognome sia corretto.
	 * - assertEquals email: verifica che l'email sia corretta.
	 */
	@Test
	void testCostruttoreValido() {
	    Utente utente = new Utente("Ahmed", "Ibrahim", "ahmed.ibrahim@email.com");
	    assertEquals("Ahmed", utente.getNome());
	    assertEquals("Ibrahim", utente.getCognome());
	    assertEquals("ahmed.ibrahim@email.com", utente.getEmail());
	}

	/**
	 * Verifica che venga sollevata un'eccezione se si tenta di creare un utente con email null.
	 * Assert verificato:
	 * - assertThrows: verifica che venga sollevata un'eccezione IllegalArgumentException.
	 */
	@Test
	void testCostruttoreEmailNull() {
	    assertThrows(IllegalArgumentException.class, () -> new Utente("Ahmed", "Ibrahim", null));
	}
    /**
     * Verifica validazione formato email.
     * Assert verificati per ogni email non valida:
     * - assertThrows: verifica eccezione
     * - assertEquals messaggio: verifica messaggio errore
     */
    @Test
    void testCostruttoreEmailNonValide() {
      
        ArrayList<String> emailNonValide = new ArrayList<>();
        emailNonValide.add("emailsenzachiocciola.com");  
        emailNonValide.add("");                       

        for (String email : emailNonValide) {
            final String emailTest = email;
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Utente("Ahmed", "Ibrahim", emailTest);}, "Dovrebbe lanciare eccezione per email: " + emailTest);
            assertEquals("Email non valida", exception.getMessage());
        }
    }

    /**
    * Verifica funzionamento equals.
    * Assert verificati:
    * - assertTrue riflessività: oggetto uguale a se stesso
    * - assertTrue simmetria: se a=b allora b=a
    * - assertTrue transitività: se a=b e b=c allora a=c
    * - assertFalse casi diversi: verifica disuguaglianza corretta
    */
    @Test
    void testEquals() {
        Utente utente1 = new Utente("Ahmed", "Ibrahim", "ahmed.ibrahim@email.com");
        Utente utente2 = new Utente("Ahmed", "Ibrahim", "ahmed.ibrahim@email.com");
        Utente utente3 = new Utente("Ali", "Hassan", "ahmed.ibrahim@email.com");
        Utente utente4 = new Utente("Ahmed", "Ibrahim", "altro.email@email.com");
        
        assertTrue(utente1.equals(utente1));          
        assertTrue(utente1.equals(utente2));         
        assertTrue(utente2.equals(utente1));          
        assertTrue(utente1.equals(utente3));         
        assertFalse(utente1.equals(utente4));        
        assertFalse(utente1.equals(null));           
        assertFalse(utente1.equals("una stringa"));  
    }

    /**
     * Verifica coerenza hashCode con equals.
     * Assert verificati:
     * - assertEquals: oggetti uguali hanno stesso hash
     * - assertNotEquals: oggetti diversi hanno hash diversi
     */
    @Test
    void testHashCode() {
        Utente utente1 = new Utente("Ahmed", "Ibrahim", "ahmed.ibrahim@email.com");
        Utente utente2 = new Utente("Ahmed", "Ibrahim", "ahmed.ibrahim@email.com");
        Utente utente3 = new Utente("Ali", "Hassan", "ahmed.ibrahim@email.com");
        Utente utente4 = new Utente("Ahmed", "Ibrahim", "altro.email@email.com");
        
        assertEquals(utente1.hashCode(), utente2.hashCode());     
        assertEquals(utente1.hashCode(), utente3.hashCode());     
        assertNotEquals(utente1.hashCode(), utente4.hashCode());  
    }

    /**
     * Verifica funzionamento metodi getter.
     * Assert verificati:
     * - assertEquals per ogni campo: verifica valori corretti
     */
    @Test
    void testGetters() {
        Utente utente = new Utente("Ahmed", "Ibrahim", "ahmed.ibrahim@email.com");
        assertEquals("Ahmed", utente.getNome());
        assertEquals("Ibrahim", utente.getCognome());
        assertEquals("ahmed.ibrahim@email.com", utente.getEmail());
    }
}