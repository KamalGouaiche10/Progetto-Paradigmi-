package upoBacheca;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Package upoBacheca - Sistema di gestione di una bacheca di annunci
 * Questo package implementa un sistema di bacheca dove gli utenti possono pubblicare 
 * e gestire annunci di acquisto e vendita.
 */

/**
 * Classe astratta che definisce la struttura base di un annuncio nel sistema.
 * Implementa Serializable per permettere il salvataggio su file degli annunci.
 * 
 * @author DIABI, GOUAICHE
 */


public abstract class Annuncio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static int id = 0; 
    private static int counter = 0; 
    private final String articolo;
    private final double prezzo;
    private final ArrayList<String> paroleChiave;
    private final Utente creatore;
    private final int currentId; 

    /**
     * Costruttore per creare un nuovo annuncio.
     * 
     * @param counter      Contatore incrementale per gli ID
     * @param articolo     Nome dell'articolo dell'annuncio
     * @param prezzo       Prezzo dell'articolo
     * @param paroleChiave Lista di parole chiave associate all'annuncio
     * @param creatore     Utente che ha creato l'annuncio
     * @throws IllegalArgumentException se il prezzo è minore o uguale a 0
     */

    public Annuncio(int counter, String articolo, double prezzo, ArrayList<String> paroleChiave, Utente creatore) {
        id = Math.max(id, counter); 
        this.currentId = ++id; 
        this.articolo = articolo;
        this.prezzo = prezzo;
        if(this.prezzo <= 0) {
            throw new IllegalArgumentException("Il prezzo deve essere maggiore da 0");
        }
        this.paroleChiave = paroleChiave;
        this.creatore = creatore;
    }
    
    
    /**
     * Restituisce l'ID univoco dell'annuncio.
     * @return ID dell'annuncio
     */
    public int getId() {
        return currentId; 
    }

    /**
     * Restituisce il nome dell'articolo.
     * @return Nome dell'articolo
     */
    public String getArticolo() {
        return articolo;
    }
    
    /**
     * Restituisce il prezzo dell'articolo.
     * @return Prezzo dell'articolo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Restituisce la lista delle parole chiave associate all'annuncio.
     * @return ArrayList di parole chiave
     */
    public ArrayList<String> getParoleChiave() {
        return paroleChiave;
    }

    /**
     * Restituisce l'utente che ha creato l'annuncio.
     * @return Utente creatore
     */
    public Utente getCreatore() {
        return creatore;
    }

    /**
     * Aggiunge una nuova parola chiave all'annuncio.
     * @param parola Nuova parola chiave da aggiungere
     */
    public void aggiungiParolaChiave(String parola) {
        paroleChiave.add(parola);
    }
    
    /**
     * Rimuove una parola chiave dall'annuncio.
     * @param parola Parola chiave da rimuovere
     */
    public void eliminaParolaChiave(String parola) {
        paroleChiave.remove(parola);
    }

    /**
     * Metodo astratto per verificare se l'annuncio è scaduto.
     * @return true se l'annuncio è scaduto, false altrimenti
     */
    public abstract boolean IsScaduto();

    @Override
    public String toString() {
        return String.format("Annuncio [id=%03d, articolo=%s, prezzo=%.2f, paroleChiave=%s, creatore=%s]",
            currentId, articolo, prezzo, paroleChiave, creatore);
    }

    public static int getCounter() {
        return counter;
    }
}