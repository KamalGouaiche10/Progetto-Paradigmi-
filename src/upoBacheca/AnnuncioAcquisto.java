package upoBacheca;

import java.util.ArrayList;

/**
 * Classe che rappresenta un annuncio di acquisto nel sistema.
 * Estende la classe Annuncio e implementa la logica specifica per gli annunci di acquisto.
 * Gli annunci di acquisto non hanno scadenza.
 * 
 * @author DIABI, GOUAICHE
 */
public class AnnuncioAcquisto extends Annuncio {
	/**
     * Costruttore per creare un nuovo annuncio di acquisto.
     * 
     * @param id          ID dell'annuncio
     * @param articolo    Nome dell'articolo richiesto
     * @param prezzoMax   Prezzo massimo che si è disposti a pagare
     * @param paroleChiave Lista di parole chiave associate all'annuncio
     * @param creatore    Utente che ha creato l'annuncio
     */
	private static final long serialVersionUID = 1L;
	public AnnuncioAcquisto(int id, String articolo, double prezzoMax, ArrayList<String> paroleChiave, Utente creatore) {
        super(id, articolo, prezzoMax, paroleChiave, creatore);
    }
    /**
     * Determina se l'annuncio di acquisto è scaduto.
     * Per gli annunci di acquisto, questo metodo restituisce sempre `false` 
     * poiché non è prevista una scadenza predefinita.
     * 
     * @return `false`, indicando che l'annuncio non è scaduto.
     */
	
	 /**
     * Gli annunci di acquisto non scadono mai.
     * @return false sempre, indicando che l'annuncio non scade
     */
    @Override
    public boolean IsScaduto() {
        return false;
    }

    /**
     * Fornisce una rappresentazione testuale dell'annuncio di acquisto.
     * Include i dettagli dell'annuncio e un'indicazione che si tratta di un "Annuncio di acquisto".
     * 
     * @return Una rappresentazione testuale dell'annuncio.
     */
    @Override
    public String toString() {
        return "AnnuncioAcquisto {" +
               "ID: " + getId() +
               ", Articolo: " + getArticolo() +
               ", Prezzo Massimo: " + getPrezzo() +
               ", Parole Chiave: " + getParoleChiave() +
               ", Creatore: " + getCreatore().getNome() + ", " +  getCreatore().getCognome() + ",  [" + getCreatore().getEmail() + "]"+
               "}";
    }
}
