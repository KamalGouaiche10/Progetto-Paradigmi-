
package upoUtil;

import upoBacheca.Annuncio;

/**
 * Implementazione del ComparatoreAnnuncio che confronta gli annunci in base al prezzo.
 * Fornisce un ordinamento naturale degli annunci basato sul loro valore monetario.
 * 
 * Caratteristiche dell'ordinamento:
 * - Gli annunci sono ordinati in ordine crescente di prezzo
 * - Utilizza Double.compare per gestire correttamente i casi speciali dei numeri in virgola mobile
 * 
 * @author DIABI, GOUAICHE
 */
public class ComparatorAnnuncioPerPrezzo implements ComparatoreAnnuncio {

	 /**
     * Confronta due annunci in base al loro prezzo.
     * L'implementazione garantisce un ordinamento stabile e consistente.
     * 
     * @param a1 il primo annuncio da confrontare
     * @param a2 il secondo annuncio da confrontare
     * @return un intero negativo se a1.prezzo < a2.prezzo,
     *         zero se a1.prezzo == a2.prezzo,
     *         un intero positivo se a1.prezzo > a2.prezzo
     */
   
    @Override
    public int compare(Annuncio a1, Annuncio a2) {
        return Double.compare(a1.getPrezzo(), a2.getPrezzo());
    }
}