package upoBacheca;
/**
 * Classe che rappresenta un utente del sistema.
 * Contiene le informazioni base dell'utente e implementa Serializable per la persistenza.
 * L'uguaglianza tra utenti è basata sull'indirizzo email.
 * 
 * @author DIABI , GOUAICHE
 */
import java.io.Serializable;
import java.util.Objects;

public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String nome;
    private final String cognome;
    private final String email;

    /**
     * Costruttore per creare un nuovo utente.
     * Valida i dati in input per assicurare la correttezza.
     * 
     * @param nome Nome dell'utente
     * @param cognome Cognome dell'utente
     * @param email Email dell'utente (deve contenere @)
     * @throws IllegalArgumentException se i dati non sono validi
     */
    public Utente(String nome, String cognome, String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email non valida");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome non valido");
        }
        if (cognome == null || cognome.isEmpty()) {
            throw new IllegalArgumentException("Cognome non valido");
        }
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
    /**
     * Restituisce il nome dell'utente.
     * 
     * @return il nome dell'utente
     */
    
    
    public String getNome() {
        return nome;
    }
    /**
     * Restituisce il cognome dell'utente.
     * 
     * @return il cognome dell'utente
     */
   
    public String getCognome() {
        return cognome;
    }
    /**
     * Restituisce l'email dell'utente.
     * 
     * @return l'email dell'utente
     */
    public String getEmail() {
        return email;
    }
    /**
     * Verifica se due utenti sono uguali confrontando le loro email.
     * 
     * @param o l'oggetto da confrontare
     * @return true se gli utenti hanno la stessa email, false altrimenti
     */
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(email, utente.email);
    }
    /**
     * Calcola l'hash code dell'utente basato sull'email.
     * 
     * @return il codice hash dell'utente
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
