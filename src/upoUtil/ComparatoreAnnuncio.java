package upoUtil;

import java.util.Comparator;

import upoBacheca.Annuncio;

/**
* Interfaccia che definisce il contratto per il confronto tra annunci.
* Permette di implementare diversi criteri di ordinamento per gli annunci
* seguendo il pattern Strategy.
* 
* L'interfaccia può essere implementata per fornire diversi criteri di ordinamento:
* - Ordinamento per prezzo
* - Ordinamento per data
* - Ordinamento per altre caratteristiche degli annunci
* 
* @author DIABI, GOUAICHE
*/
public interface ComparatoreAnnuncio extends Comparator<Annuncio> {
    
	/**
     * Confronta due annunci secondo un criterio specifico.
     * L'implementazione deve garantire le proprietà di un confronto totale:
     * - Antisimmetria: se compare(a,b) > 0 allora compare(b,a) < 0
     * - Transitività: se compare(a,b) > 0 e compare(b,c) > 0 allora compare(a,c) > 0
     * 
     * @param a1 il primo annuncio da confrontare
     * @param a2 il secondo annuncio da confrontare
     * @return un intero negativo, zero, o positivo se il primo annuncio 
     *         è rispettivamente minore, uguale o maggiore del secondo
     */
    int compare(Annuncio a1, Annuncio a2);
}