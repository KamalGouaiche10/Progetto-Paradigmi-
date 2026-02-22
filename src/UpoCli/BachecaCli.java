package UpoCli;


import java.time.LocalDate;
import java.util.*;

import upoBacheca.Annuncio;
import upoBacheca.AnnuncioAcquisto;
import upoBacheca.AnnuncioVendita;
import upoBacheca.Bacheca;
import upoBacheca.BachecaImplementa;
import upoBacheca.Utente;

/**
 * Classe che implementa un'interfaccia a riga di comando (CLI) per la gestione di una bacheca di annunci.
 * Permette agli utenti di interagire con il sistema di bacheca attraverso un menu testuale,
 * offrendo funzionalità come inserimento annunci, ricerca, e gestione delle parole chiave.
 * 
 * @author Gouaiche, Diabi
 */
public class BachecaCli {
    private final Bacheca bacheca;
    private final Scanner scanner;

    /**
     * Costruttore della classe BachecaCli.
     * Inizializza una nuova istanza del gestore CLI con una bacheca specificata.
     *
     * @param bacheca L'implementazione della Bacheca da utilizzare per la gestione degli annunci
     */
    public BachecaCli(Bacheca bacheca) {
        this.bacheca = bacheca;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Visualizza il menu principale dell'applicazione con tutte le opzioni disponibili.
     * Le opzioni includono:
     * 1. Inserimento di un nuovo annuncio
     * 2. Ricerca di annunci esistenti
     * 3. Pulizia della bacheca
     * 4. Aggiunta di parole chiave agli annunci esistenti
     * 5. Ordina oggetti secondo il loro prezzo
     * 0. Uscita dal programma
     */
    private void mostraMenu() {
        System.out.println("\n=== BACHECA ANNUNCI ===");
        System.out.println("1. Inserisci annuncio");
        System.out.println("2. Cerca annunci");
        System.out.println("3. Pulisci bacheca");
        System.out.println("4. Aggiungi parola chiave");
        System.out.println("5. Mostra annunci ordinati per prezzo");
        System.out.println("0. Esci");
    }
    /**
     * Gestisce l'aggiunta di una nuova parola chiave a un annuncio esistente.
     * Richiede all'utente:
     * - Email dell'utente proprietario dell'annuncio
     * - Nome e cognome dell'utente
     * - ID dell'annuncio da modificare
     * - Nuova parola chiave da aggiungere
     * 
     * @throws IllegalArgumentException se i dati inseriti non sono validi o l'utente non è autorizzato
     */
    private void aggiungiParolaChiave() {
        System.out.print("Email utente: ");
        String email = scanner.nextLine();
        System.out.print("nome utente: ");
        String nome = scanner.nextLine();
        System.out.print("cognome utente: ");
        String cognome = scanner.nextLine();
        System.out.print("ID annuncio: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nuova parola chiave: ");
        String parolaChiave = scanner.nextLine();

        try {
            Utente utente = new Utente(nome, cognome, email); // Nome e cognome non necessari per il confronto
            bacheca.aggiungiParolaChiave(utente, id, parolaChiave);
            System.out.println("Parola chiave aggiunta con successo");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
    /**
     * Avvia l'interfaccia CLI e gestisce il ciclo principale del programma.
     * Mostra il menu principale e gestisce le scelte dell'utente fino alla chiusura.
     */
    public void avvia() {
        boolean continua = true;
        while (continua) {
            mostraMenu();
            int scelta = leggiIntero("Scelta: ");
            
            switch (scelta) {
                case 1:
                    eseguiOperazione(this::inserisciAnnuncio);
                    break;
                case 2:
                    eseguiOperazione(this::cercaAnnunci);
                    break;
                case 3:
                    eseguiOperazione(this::pulisciBacheca);
                    break;
                case 4:
                    eseguiOperazione(this::aggiungiParolaChiave);
                    break;
                case 5:
                    eseguiOperazione(this::mostraAnnunciOrdinatiPerPrezzo); 
                    break;
                case 0:
                    continua = false;
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }

    /**
     * Esegue un'operazione specifica gestendo eventuali errori.
     * In caso di errore, offre all'utente la possibilità di riprovare l'operazione.
     *
     * @param operazione L'operazione da eseguire, fornita come Runnable
     */
    private void eseguiOperazione(Runnable operazione) {
        boolean operazioneCompletata = false;
        while (!operazioneCompletata) {
            try {
                operazione.run();
                operazioneCompletata = true;
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
                System.out.print("Vuoi riprovare? (s/n): ");
                String risposta = scanner.nextLine().trim().toLowerCase();
                if (!risposta.equals("s")) {
                    break;
                }
            }
        }
    }

    /**
     * Gestisce l'inserimento di un nuovo annuncio nella bacheca.
     * Permette di inserire sia annunci di acquisto che di vendita, raccogliendo:
     * - Dati dell'utente (nome, cognome, email)
     * - Tipo di annuncio (acquisto/vendita)
     * - Dettagli dell'articolo
     * - Prezzo
     * - Parole chiave
     * - Data di scadenza (solo per annunci di vendita)
     * 
     * Per gli annunci di acquisto, cerca e mostra automaticamente annunci correlati.
     *
     * @throws IllegalArgumentException se ci sono errori nell'inserimento dei dati
     */
    private void inserisciAnnuncio() {
        try {
            System.out.println("\nInserimento nuovo annuncio");
            System.out.print("Nome utente: ");
            String nome = scanner.nextLine();
            System.out.print("Cognome utente: ");
            String cognome = scanner.nextLine();
            System.out.print("Email utente: ");
            String email = scanner.nextLine();
            
            Utente utente = new Utente(nome, cognome, email);
            
            System.out.print("Tipo annuncio (A=Acquisto, V=Vendita): ");
            String tipo = scanner.nextLine().toUpperCase();
            
            System.out.print("Articolo: ");
            String articolo = scanner.nextLine();
            
            System.out.print("Prezzo: ");
            double prezzo = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Parole chiave (separate da virgola): ");
            String[] paroleArray = scanner.nextLine().split(",");
            ArrayList<String> paroleChiave = new ArrayList<>();
            for (String parola : paroleArray) {
                if (!parola.trim().isEmpty()) {
                    paroleChiave.add(parola.trim());
                }
            }
            
            Annuncio annuncio;
            if (tipo.equals("A")) {
                annuncio = new AnnuncioAcquisto(Annuncio.getCounter(), articolo, prezzo, paroleChiave, utente);
                bacheca.aggiungiAnnuncio(annuncio);
                System.out.println("Annuncio di acquisto inserito con successo");
                System.out.println("Cercando annunci correlati...");
                ArrayList<Annuncio> annunciCorrelati = bacheca.CercaAnnunncioPerParolaChiave(String.join(",", paroleChiave));
                if (!annunciCorrelati.isEmpty()) {
                    System.out.println("\nAnnunci correlati trovati:");
                    for (Annuncio correlato : annunciCorrelati) {
                        if (!correlato.equals(annuncio)) { // Esclude l'annuncio appena inserito
                            System.out.println(correlato.toString());
                        }
                    }
                } else {
                    System.out.println("Nessun annuncio correlato trovato.");
                }
            } else {
                System.out.print("Data scadenza (YYYY-MM-DD): ");
                LocalDate dataScadenza = LocalDate.parse(scanner.nextLine());
                annuncio = new AnnuncioVendita(Annuncio.getCounter(), articolo, prezzo, paroleChiave, utente, dataScadenza);
                bacheca.aggiungiAnnuncio(annuncio);
                System.out.println("Annuncio di vendita inserito con successo");
            }
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Errore nell'inserimento: " + e.getMessage());
        }
    }
    /**
     * Implementa la funzionalità di ricerca annunci nella bacheca.
     * Permette all'utente di cercare annunci utilizzando una o più parole chiave
     * separate da virgola.
     *
     * @throws IllegalArgumentException se la stringa di ricerca è vuota
     */
    private void cercaAnnunci() {
        System.out.print("Inserisci parole chiave (separate da virgola): ");
        String keywords = scanner.nextLine();
        if (keywords.isEmpty()) {
            throw new IllegalArgumentException("Parole chiave non valide");
        }
        
        ArrayList<Annuncio> risultati = bacheca.CercaAnnunncioPerParolaChiave(keywords);
        System.out.println("\nRisultati della ricerca:");
        for (Annuncio annuncio : risultati) {
            System.out.println(annuncio.toString());
        }
        System.out.println("\nRicerca completata - Trovati " + risultati.size() + " annunci");
    }

    /**
     * Esegue la pulizia della bacheca, rimuovendo gli annunci scaduti.
     * Conferma l'operazione con un messaggio all'utente.
     */
    private void pulisciBacheca() {
        bacheca.pulisciBacheca();
        System.out.println("Bacheca pulita");
    }
    /**
    * Utility per leggere un numero intero dall'input dell'utente.
    * Continua a richiedere l'input finché non viene inserito un numero valido.
    *
    * @param messaggio Il messaggio da mostrare all'utente per la richiesta di input
    * @return Il numero intero inserito dall'utente
    */    private int leggiIntero(String messaggio) {
        while (true) {
            try {
                System.out.print(messaggio);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Inserire un numero valido");
            }
        }
    }
    /**
     * Visualizza tutti gli annunci ordinati per prezzo in ordine crescente.
     * Recupera la lista ordinata dall'implementazione della bacheca 
     * e stampa ogni annuncio su una nuova riga.
     */
    private void mostraAnnunciOrdinatiPerPrezzo() {
        ArrayList<Annuncio> annunciOrdinati = ((BachecaImplementa) bacheca).getAnnunciOrdinatiPerPrezzo();
        System.out.println("\nAnnunci ordinati per prezzo:");
        for (Annuncio annuncio : annunciOrdinati) {
            System.out.println(annuncio.toString());
        }
    }

}
