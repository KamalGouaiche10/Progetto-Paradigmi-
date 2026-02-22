package upoBacheca;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Classe che rappresenta un annuncio di vendita nel sistema.
 * Estende la classe Annuncio e implementa la logica specifica per gli annunci di vendita.
 * Gli annunci di vendita hanno una data di scadenza oltre la quale non sono più validi.
 * 
 * @author [Nome Autore]
 */



public class AnnuncioVendita extends Annuncio {

	private static final long serialVersionUID = 1L;
	
    private final LocalDate dataScadenza;

    /**
     * Crea un nuovo annuncio di vendita.
     * 
     * @param id           L'ID dell'annuncio
     * @param articolo     Il nome dell'articolo in vendita
     * @param prezzo       Il prezzo dell'articolo
     * @param paroleChiave Lista delle parole chiave associate all'annuncio
     * @param creatore     L'utente che ha creato l'annuncio
     * @param dataScadenza La data di scadenza dell'annuncio
     * @throws IllegalArgumentException se la data di scadenza è nel passato
     */
    public AnnuncioVendita(int id, String articolo, double prezzo, ArrayList<String> paroleChiave, Utente creatore, LocalDate dataScadenza) {
        super(id, articolo, prezzo, paroleChiave, creatore);
        if (dataScadenza.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("data di scadenza errata, non può essere nel passato");
        }
        this.dataScadenza = dataScadenza;
    }
    /**
     * Restituisce la data di scadenza dell'annuncio.
     * 
     * @return la data di scadenza dell'annuncio
     */
    
    public LocalDate getDataScadenza() {
        return dataScadenza;
    }
    
    /**
     * Verifica se l'annuncio è scaduto confrontando la data attuale con la data di scadenza.
     * 
     * @return true se l'annuncio è scaduto, false altrimenti
     */
    @Override
    public boolean IsScaduto() {
        return LocalDate.now().isAfter(dataScadenza);
    }

  
    @Override
    public String toString() {
        return "Annuncio Vendita {" + "ID: " + getId() +", Articolo: " + getArticolo() + ", Prezzo: " + getPrezzo() +", Parole Chiave: " + getParoleChiave() +", Venditore: " + getCreatore().getNome() + ", " +  getCreatore().getCognome() + ",  [" + getCreatore().getEmail() + "]" + ", Data Scadenza: " + dataScadenza +"}";
    }
}
