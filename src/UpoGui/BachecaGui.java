package UpoGui;

import upoBacheca.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Classe principale per l'interfaccia grafica della bacheca annunci.
 * Fornisce una GUI interattiva per gestire una bacheca di annunci di acquisto e vendita.
 * 
 * La classe offre funzionalità per:
 * - Inserire nuovi annunci (sia di acquisto che di vendita)
 * - Rimuovere annunci esistenti
 * - Cercare annunci per parola chiave
 * - Aggiungere parole chiave agli annunci esistenti
 * - Pulire completamente la bacheca
 * 
 * @author Gouaiche, Diabi
 */
public class BachecaGui {
    private final Bacheca bacheca;
    private JFrame frame;
    private JTextArea areaAnnunci;

    /**
     * Costruisce una nuova interfaccia grafica per la bacheca specificata.
     * 
     * @param bacheca L'oggetto Bacheca da utilizzare per la gestione degli annunci
     */

    public BachecaGui(Bacheca bacheca) {
        this.bacheca = bacheca;
    }

    /**
     * Inizializza e mostra l'interfaccia grafica principale.
     * Crea una finestra con pulsanti per tutte le operazioni disponibili e
     * un'area di testo per visualizzare gli annunci.
     */
    public void mostraGUI() {
        frame = new JFrame("Bacheca Annunci");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        areaAnnunci = new JTextArea();
        areaAnnunci.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaAnnunci);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton inserisciButton = new JButton("Inserisci Annuncio");
        JButton rimuoviButton = new JButton("Rimuovi Annuncio");
        JButton cercaButton = new JButton("Cerca");
        JButton pulisciButton = new JButton("Pulisci Bacheca");
        JButton ordinaPerPrezzoButton = new JButton("Ordina per prezzo");
        JButton aggiungiParolaChiaveButton = new JButton("Aggiungi Parola Chiave");
        JButton rimuoviParolaChiaveButton = new JButton("Rimuovi Parola Chiave");
       
       


        buttonPanel.add(inserisciButton);
        buttonPanel.add(rimuoviButton);
        buttonPanel.add(cercaButton);
        buttonPanel.add(pulisciButton); 
        buttonPanel.add(ordinaPerPrezzoButton);
        buttonPanel.add(aggiungiParolaChiaveButton);
        buttonPanel.add(rimuoviParolaChiaveButton);
        
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        inserisciButton.addActionListener(e -> inserisciAnnuncio());
        rimuoviButton.addActionListener(e -> rimuoviAnnuncio());
        cercaButton.addActionListener(e -> cercaAnnunci());
        pulisciButton.addActionListener(e -> pulisciBacheca());
        ordinaPerPrezzoButton.addActionListener(e -> mostraAnnunciOrdinatiPerPrezzo());
        aggiungiParolaChiaveButton.addActionListener(e -> aggiungiParolaChiave());
        rimuoviParolaChiaveButton.addActionListener(e -> rimuoviParolaChiave());
        
        frame.add(mainPanel);
        frame.setVisible(true);

        aggiornaListaAnnunci();
    }
    /**
     * Gestisce l'inserimento di un nuovo annuncio tramite finestre di dialogo.
     * Raccoglie i dati dell'utente (nome, cognome, email) e dell'annuncio
     * (articolo, prezzo, parole chiave, tipo). Per gli annunci di vendita
     * richiede anche una data di scadenza.
     * 
     * @throws Exception se si verificano errori durante l'inserimento dei dati
     */
    private void inserisciAnnuncio() {
        try {
            JTextField nomeField = new JTextField();
            JTextField cognomeField = new JTextField();
            JTextField emailField = new JTextField();

            Object[] utenteFields = {"Nome:", nomeField, "Cognome:", cognomeField, "Email:", emailField};

            int result = JOptionPane.showConfirmDialog(frame, utenteFields,
                    "Inserisci dati utente", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) return;

            Utente utente = new Utente(nomeField.getText(), cognomeField.getText(), emailField.getText());

            JTextField articoloField = new JTextField();
            JTextField prezzoField = new JTextField();
            JTextField paroleChiaveField = new JTextField();
            String[] tipi = {"Acquisto", "Vendita"};
            JComboBox<String> tipoBox = new JComboBox<>(tipi);

            Object[] annuncioFields = {"Articolo:", articoloField, "Prezzo:", prezzoField, "Parole chiave (separate da virgola):", paroleChiaveField, "Tipo:", tipoBox};

            result = JOptionPane.showConfirmDialog(frame, annuncioFields, "Inserisci dati annuncio", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) return;

            double prezzo = Double.parseDouble(prezzoField.getText());
            ArrayList<String> paroleChiave = new ArrayList<>();
            for (String parola : paroleChiaveField.getText().split(",")) {
                if (!parola.trim().isEmpty()) {
                    paroleChiave.add(parola.trim());
                }
            }

            if (tipoBox.getSelectedItem().equals("Vendita")) {
                String dataStr = JOptionPane.showInputDialog(frame, "Inserisci data scadenza (Anno-Mese-Giorno):");
                LocalDate dataScadenza = LocalDate.parse(dataStr);

                bacheca.aggiungiAnnuncio(new AnnuncioVendita(Annuncio.getCounter(), articoloField.getText(), prezzo, paroleChiave, utente, dataScadenza));
            } else {
                bacheca.aggiungiAnnuncio(new AnnuncioAcquisto(Annuncio.getCounter(), articoloField.getText(), prezzo, paroleChiave, utente));
            }

            aggiornaListaAnnunci();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void mostraAnnunciOrdinatiPerPrezzo() {
        ArrayList<Annuncio> annunciOrdinati = ((BachecaImplementa) bacheca).getAnnunciOrdinatiPerPrezzo();
        StringBuilder sb = new StringBuilder();
        for (Annuncio annuncio : annunciOrdinati) {
            sb.append(annuncio.toString()).append("\n");
        }
        areaAnnunci.setText(sb.toString());
    }

    /**
    * Permette di aggiungere una nuova parola chiave a un annuncio esistente.
    * Richiede autenticazione dell'utente tramite nome, cognome ed email,
    * e l'ID dell'annuncio da modificare.
    * 
    * @throws Exception se l'utente non è autorizzato o l'annuncio non esiste
    */
    private void aggiungiParolaChiave() {
        try {
            String nome = JOptionPane.showInputDialog(frame, "Inserisci il tuo nome:");
            if (nome == null) return;

            String cognome = JOptionPane.showInputDialog(frame, "Inserisci il tuo cognome:");
            if (cognome == null) return;

            String email = JOptionPane.showInputDialog(frame, "Inserisci la tua email:");
            if (email == null) return;

            String idStr = JOptionPane.showInputDialog(frame, "Inserisci ID annuncio:");
            if (idStr == null) return;

            String parolaChiave = JOptionPane.showInputDialog(frame, "Inserisci la nuova parola chiave:");
            if (parolaChiave == null) return;

            int id = Integer.parseInt(idStr);
            Utente utente = new Utente(nome, cognome, email);
            bacheca.aggiungiParolaChiave(utente, id, parolaChiave);

            aggiornaListaAnnunci();
            JOptionPane.showMessageDialog(frame, "Parola chiave aggiunta con successo",
                                        "Successo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage(),
                                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void rimuoviParolaChiave() {
        try {
            String nome = JOptionPane.showInputDialog(frame, "Inserisci il tuo nome:");
            if (nome == null) return;

            String cognome = JOptionPane.showInputDialog(frame, "Inserisci il tuo cognome:");
            if (cognome == null) return;

            String email = JOptionPane.showInputDialog(frame, "Inserisci la tua email:");
            if (email == null) return;

            String idStr = JOptionPane.showInputDialog(frame, "Inserisci ID annuncio:");
            if (idStr == null) return;

            String parolaChiave = JOptionPane.showInputDialog(frame, "Inserisci la parola chiave da rimuovere:");
            if (parolaChiave == null) return;

            int id = Integer.parseInt(idStr);
            Utente utente = new Utente(nome, cognome, email);
            bacheca.rimuoviParolaChiave(utente, id, parolaChiave);

            aggiornaListaAnnunci();
            JOptionPane.showMessageDialog(frame, "Parola chiave rimossa con successo",
                                        "Successo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage(),
                                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Gestisce la rimozione di un annuncio dalla bacheca.
     * Richiede autenticazione dell'utente tramite nome, cognome ed email,
     * e l'ID dell'annuncio da rimuovere.
     * 
     * @throws Exception se l'utente non è autorizzato o l'annuncio non esiste
     */
    private void rimuoviAnnuncio() {
        try {
            String nome = JOptionPane.showInputDialog(frame, "Inserisci il tuo nome:");
            if (nome == null) return;

            String cognome = JOptionPane.showInputDialog(frame, "Inserisci il tuo cognome:");
            if (cognome == null) return;

            String email = JOptionPane.showInputDialog(frame, "Inserisci la tua email:");
            if (email == null) return;

            String idStr = JOptionPane.showInputDialog(frame, "Inserisci ID annuncio:");
            if (idStr == null) return;

            int id = Integer.parseInt(idStr);
            Utente utente = new Utente(nome, cognome, email);
            bacheca.removeAnnuncio(utente, id);

            aggiornaListaAnnunci();
            JOptionPane.showMessageDialog(frame, "Annuncio rimosso con successo",
                                        "Successo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage(),
                                        "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Implementa la funzionalità di ricerca annunci per parola chiave.
     * Mostra i risultati nell'area di testo principale.
     * 
     * @throws Exception se si verificano errori durante la ricerca
     */
    private void cercaAnnunci() {
        try {
            String parolaChiave = JOptionPane.showInputDialog(frame, "Inserisci parola chiave da cercare:");
            if (parolaChiave == null) return;

            ArrayList<Annuncio> risultati = bacheca.CercaAnnunncioPerParolaChiave(parolaChiave);

            StringBuilder sb = new StringBuilder();
            sb.append("Risultati della ricerca:\n\n");
            for (Annuncio annuncio : risultati) {
                sb.append(annuncio.toString()).append("\n");
            }

            areaAnnunci.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Rimuove tutti gli annunci dalla bacheca.
     * Mostra un messaggio di conferma all'utente.
     * 
     * @throws Exception se si verificano errori durante la pulizia
     */
    private void pulisciBacheca() {
        try {
            bacheca.pulisciBacheca();
            aggiornaListaAnnunci();
            JOptionPane.showMessageDialog(frame, "Bacheca pulita con successo", "Informazione", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aggiorna l'area di testo principale con la lista corrente degli annunci.
     * Utilizza l'iteratore della bacheca per accedere a tutti gli annunci.
     */
    private void aggiornaListaAnnunci() {
        StringBuilder sb = new StringBuilder();
        var iterator = bacheca.iteratore();
        while (iterator.hasNext()) {
            sb.append(iterator.next().toString()).append("\n");
        }
        areaAnnunci.setText(sb.toString());
    }
}
