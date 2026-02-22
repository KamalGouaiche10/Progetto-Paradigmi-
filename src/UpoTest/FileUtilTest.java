package UpoTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import upoBacheca.Bacheca;
import upoBacheca.BachecaImplementa;
import upoBacheca.AnnuncioVendita;
import upoBacheca.Utente;
import upoUtil.FileUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe di test per verificare il corretto funzionamento delle operazioni di file I/O della classe FileUtil.
 * Testa il salvataggio e il caricamento della bacheca su file.
 * @author Gouaiche, Diabi
 */
class FileUtilTest {

    private Bacheca bacheca;
    private Utente utente;
    private AnnuncioVendita annuncio;
    private static final String FILE_PATH = "bacheca_test.dat";

    /**
     * Inizializza l'ambiente di test prima di ogni metodo.
     * Crea una bacheca di test con un annuncio di vendita.
     */
    @BeforeEach
    void setUp() {
        bacheca = new BachecaImplementa();
        utente = new Utente("Mario", "Rossi", "mario.rossi@email.com");
        annuncio = new AnnuncioVendita(1, "iPhone", 800.0, new ArrayList<>(), utente, LocalDate.now().plusDays(7));
        bacheca.aggiungiAnnuncio(annuncio);
    }
    /**
     * Verifica che la bacheca venga salvata correttamente su file.
     * Assert verificati:
     * - assertDoesNotThrow: verifica che il salvataggio avvenga senza errori.
     */
    @Test
    void testSalvaBacheca() {
        assertDoesNotThrow(() -> FileUtil.salvaBacheca(bacheca, FILE_PATH));
    }

    /**
     * Verifica che la bacheca venga caricata correttamente da file.
     * Assert verificati:
     * - assertDoesNotThrow salvataggio: verifica che il salvataggio preliminare avvenga senza errori.
     * - assertDoesNotThrow caricamento: verifica che il caricamento avvenga senza errori.
     * - assertNotNull: verifica che i dati caricati non siano null.
     * - assertTrue(hasNext): verifica che ci siano annunci nella bacheca caricata.
     * - assertEquals: verifica che i dati caricati siano corretti.
     */
    @Test
    void testCaricaBacheca() {
        assertDoesNotThrow(() -> FileUtil.salvaBacheca(bacheca, FILE_PATH));

        Bacheca bachecaCaricata = assertDoesNotThrow(() -> FileUtil.caricaBacheca(FILE_PATH));
        assertNotNull(bachecaCaricata);
        assertTrue(bachecaCaricata.iteratore().hasNext());
        assertEquals(annuncio.toString(), bachecaCaricata.iteratore().next().toString());
    }
    /**
     * Verifica la gestione degli errori nel caricamento da file inesistente.
     * Assert verificati:
     * - assertThrows: verifica corretta gestione file non trovato
     */
    @Test
    void testCaricaBachecaFileNonEsistente() {
        assertThrows(IOException.class, () -> FileUtil.caricaBacheca("file_inesistente.dat"));
    }

    /**
     * Verifica la gestione degli errori nel salvataggio su percorso non valido.
     * Assert verificati:
     * - assertThrows: verifica corretta gestione percorso invalido
     */
    @Test
    void testSalvaBachecaPercorsoNonValido() {
        assertThrows(IOException.class, () -> FileUtil.salvaBacheca(bacheca, "/percorso/non/valido/bacheca.dat"));
    }
}