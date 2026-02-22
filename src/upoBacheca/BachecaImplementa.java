package upoBacheca;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import upoUtil.FileUtil;
import upoUtil.ComparatorAnnuncioPerPrezzo;
/**
 * Implementazione dell'interfaccia Bacheca che gestisce una lista di annunci.
 * Fornisce funzionalità per la gestione degli annunci inclusa la persistenza su file.
 * 
 * @author DIABI, GOUAICHE
 */
public class BachecaImplementa implements Bacheca, Serializable {

    private static final long serialVersionUID = 1L;
    
    private ArrayList<Annuncio> annunci;
    
    
    public BachecaImplementa() {
        this.annunci = new ArrayList<>();
    }
    
    
    /**
     * Gestisce l'aggiunta di un annuncio e cerca annunci correlati.
     * Per annunci di acquisto, cerca e mostra annunci di vendita correlati.
     */
    @Override
    public void aggiungiAnnuncio(Annuncio annuncio) {
        if (annuncio == null) {
            throw new IllegalArgumentException("L'annuncio non può essere null");
        }
        annunci.add(annuncio);
        
       
        if (annuncio instanceof AnnuncioAcquisto) {
            ArrayList<Annuncio> annunciCorrelati = trovaAnnunciCorrelati(annuncio.getParoleChiave());
          
            annunciCorrelati.remove(annuncio);
            
            System.out.println("Annunci correlati trovati: " + annunciCorrelati.size());
            for (Annuncio a : annunciCorrelati) {
                System.out.println(a.toString());
            }
        }
    }
    
    /**
     * Rimuove un annuncio se l'utente è autorizzato.
     * Solo il creatore dell'annuncio può rimuoverlo.
     */
    @Override
    public void removeAnnuncio(Utente utente, int id) {
        if (utente == null) {
            throw new IllegalArgumentException("L'utente non può essere null");
        }
        boolean rimosso = annunci.removeIf(annuncio -> 
            annuncio.getId() == id && annuncio.getCreatore().equals(utente));
        if (!rimosso) {
            throw new IllegalArgumentException("Annuncio non trovato o non autorizzato");
        }
    }
    
    /**
     * Cerca annunci che contengono specifiche parole chiave.
     * Supporta ricerca con multiple parole chiave separate da virgola.
     */
    @Override
    public ArrayList<Annuncio> CercaAnnunncioPerParolaChiave(String paroleChiaveInput) {
        if (paroleChiaveInput == null || paroleChiaveInput.trim().isEmpty()) {
            throw new IllegalArgumentException("Le parole chiave non possono essere vuote");
        }
       
        String[] paroleChiaveArray = paroleChiaveInput.split(",");
        return trovaAnnunciCorrelati(new ArrayList<String>() {
			private static final long serialVersionUID =1L;
		{
            for (String parola : paroleChiaveArray) {
                add(parola.trim());
            }
        }});
    }
    
    /**
     * Metodo privato per trovare annunci con parole chiave correlate.
     * Usa un Set per gestire l'intersezione delle parole chiave.
     */
    private ArrayList<Annuncio> trovaAnnunciCorrelati(ArrayList<String> paroleChiaveRicerca) {
        ArrayList<Annuncio> risultati = new ArrayList<>();
        Set<String> paroleRicercaSet = new HashSet<>(paroleChiaveRicerca);
        
        for (Annuncio annuncio : annunci) {
            Set<String> paroleAnnuncioSet = new HashSet<>(annuncio.getParoleChiave());
         
            paroleAnnuncioSet.retainAll(paroleRicercaSet);
            if (!paroleAnnuncioSet.isEmpty()) {
                risultati.add(annuncio);
            }
        }
        return risultati;
    }
    
    /**
     * Restituisce un nuovo ArrayList contenente tutti gli annunci ordinati per prezzo in ordine crescente.
     * La lista originale rimane inalterata.
     * 
     * @return un nuovo ArrayList contenente tutti gli annunci ordinati per prezzo crescente
     * @see ComparatorAnnuncioPerPrezzo
     */
    public ArrayList<Annuncio> getAnnunciOrdinatiPerPrezzo() {
        ArrayList<Annuncio> annunciOrdinati = new ArrayList<>(annunci);
        annunciOrdinati.sort(new ComparatorAnnuncioPerPrezzo());
        return annunciOrdinati;
    }

    /**
     * Aggiunge una parola chiave a un annuncio esistente.
     * Salva le modifiche su file dopo l'aggiunta.
     */
    @Override
    public void aggiungiParolaChiave(Utente utente, int idAnnuncio, String parolaChiave) {
        if (parolaChiave == null || parolaChiave.trim().isEmpty()) {
            throw new IllegalArgumentException("La parola chiave non può essere vuota");
        }

        boolean trovato = false;
        for (Annuncio annuncio : annunci) {
            if (annuncio.getId() == idAnnuncio) {
                if (!annuncio.getCreatore().equals(utente)) {
                    throw new IllegalArgumentException("Non sei autorizzato a modificare questo annuncio");
                }
                annuncio.aggiungiParolaChiave(parolaChiave.trim());
                trovato = true;
                break;
            }
        }

        if (!trovato) {
            throw new IllegalArgumentException("Annuncio non trovato");
        }

        try {
            FileUtil.salvaBacheca(this, "bacheca.dat");
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvare la bacheca: " + e.getMessage());
        }
    }
    
    
    
    @Override
    public void rimuoviParolaChiave(Utente utente, int idAnnuncio, String parolaChiave) {
        if (parolaChiave == null || parolaChiave.trim().isEmpty()) {
            throw new IllegalArgumentException("La parola chiave non può essere vuota");
        }

        boolean trovato = false;
        for (Annuncio annuncio : annunci) {
            if (annuncio.getId() == idAnnuncio) {
                if (!annuncio.getCreatore().equals(utente)) {
                    throw new IllegalArgumentException("Non sei autorizzato a modificare questo annuncio");
                }
                annuncio.eliminaParolaChiave(parolaChiave.trim());
                trovato = true;
                break;
            }
        }

        if (!trovato) {
            throw new IllegalArgumentException("Annuncio non trovato");
        }

        // Salva le modifiche su file
        try {
            FileUtil.salvaBacheca(this, "bacheca.dat");
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvare la bacheca: " + e.getMessage());
        }
    }

    /**
     * Rimuove gli annunci di vendita scaduti e salva lo stato su file.
     */
    @Override
    public void pulisciBacheca() {
        annunci.removeIf(annuncio -> annuncio instanceof AnnuncioVendita && annuncio.IsScaduto());
        
        try {
            FileUtil.salvaBacheca(this, "bacheca.dat");
            System.out.println("Bacheca aggiornata: annunci scaduti rimossi.");
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvare la bacheca aggiornata: " + e.getMessage());
        }
    }

    @Override
    public Iterator<Annuncio> iteratore() {
        return annunci.iterator();
    }
}