package upoBacheca;
/**
 * Interfaccia che definisce le operazioni base di una bacheca di annunci.
 * Fornisce i metodi per la gestione degli annunci come aggiunta, rimozione e ricerca.
 * 
 * @author DIABI , GOUAICHE
 */


import java.util.ArrayList;
import java.util.Iterator;


public interface Bacheca {

    /**
     * Aggiunge un nuovo annuncio alla bacheca.
     * 
     * @param annuncio l'annuncio da aggiungere
     * @throws IllegalArgumentException se l'annuncio è null
     */
	
    void aggiungiAnnuncio(Annuncio annuncio);
    /**
     * Rimuove un annuncio dalla bacheca.
     * 
     * @param utente l'utente che richiede la rimozione
     * @param id     l'ID dell'annuncio da rimuovere
     * @throws IllegalArgumentException se l'utente è null o l'annuncio non esiste
     */
    
    void removeAnnuncio(Utente utente, int id);
    /**
     * Cerca gli annunci che contengono una specifica parola chiave.
     * 
     * @param parolaChiave la parola chiave da cercare
     * @return ArrayList contenente gli annunci che corrispondono alla ricerca
     * @throws IllegalArgumentException se la parola chiave è null o vuota
     */
    
    ArrayList<Annuncio> CercaAnnunncioPerParolaChiave(String parolChiave);
    /**
     * Rimuove tutti gli annunci dalla bacheca.
     */
    
    
    void pulisciBacheca();

    /**
     * Restituisce un iteratore per scorrere gli annunci nella bacheca.
     * 
     * @return Iterator<Annuncio> per iterare sugli annunci
     */
    Iterator<Annuncio> iteratore();



    /**
     * Aggiunge una nuova parola chiave a un annuncio esistente.
     * 
     * @param utente l'utente che richiede l'aggiunta
     * @param idAnnuncio l'ID dell'annuncio da modificare
     * @param parolaChiave la nuova parola chiave da aggiungere
     * @throws IllegalArgumentException se l'utente non è il proprietario dell'annuncio
     *         o se l'annuncio non esiste
     */
	void aggiungiParolaChiave(Utente utente, int idAnnuncio, String parolaChiave);
	
	/**
	 * Rimuove una parola chiave da un annuncio esistente.
	 * 
	 * @param utente l'utente che richiede la rimozione
	 * @param idAnnuncio l'ID dell'annuncio da modificare
	 * @param parolaChiave la parola chiave da rimuovere
	 * @throws IllegalArgumentException se l'utente non è il proprietario dell'annuncio
	 *         o se l'annuncio non esiste
	 */
	void rimuoviParolaChiave(Utente utente, int idAnnuncio, String parolaChiave);

}
