package upoMain;

import upoBacheca.*;
import UpoCli.BachecaCli;
import UpoGui.BachecaGui;
import upoUtil.FileUtil;

import javax.swing.SwingUtilities;
/**
 * Classe principale che avvia il sistema di bacheca digitale.
 * Gestisce l'avvio dell'interfaccia grafica (GUI) e dell'interfaccia a riga di comando (CLI),
 * oltre al caricamento e salvataggio dello stato della bacheca su file.
 * 
 * La classe implementa:
 * - Caricamento iniziale della bacheca da file
 * - Avvio simultaneo di GUI e CLI
 * - Salvataggio finale dello stato della bacheca
 * 
 * @author Gouaiche, Diabi
 */

public class Main {
   

    /** 
     * Percorso del file per il salvataggio persistente della bacheca.
     * Questo file viene utilizzato sia per il caricamento iniziale che per il salvataggio finale.
     */
    private static final String FILE_PATH = "bacheca.dat";

    /**
     * Metodo principale che avvia l'applicazione.
     * Esegue in sequenza:
     * 1. Caricamento della bacheca da file
     * 2. Avvio dell'interfaccia grafica in un thread separato
     * 3. Avvio dell'interfaccia CLI nel thread principale
     * 4. Salvataggio finale della bacheca
     * 
     * @param args argomenti da riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        Bacheca bacheca = caricaBachecaDaFile();
        
       
        SwingUtilities.invokeLater(() -> {BachecaGui gui = new BachecaGui(bacheca);gui.mostraGUI();});
        
     
        BachecaCli cli = new BachecaCli(bacheca);
        cli.avvia();
        
        salvaBachecaSuFile(bacheca);
    }
    /**
     * Carica lo stato della bacheca da file.
     * Gestisce eventuali errori durante il caricamento creando una nuova bacheca vuota
     * se necessario. Stampa messaggi di debug per tracciare lo stato del caricamento.
     * 
     * @return una Bacheca esistente caricata da file o una nuova istanza di BachecaImplementa
     */
    private static Bacheca caricaBachecaDaFile() {
        try {
            Bacheca bacheca = FileUtil.caricaBacheca(FILE_PATH);
            System.out.println("DEBUG: Annunci caricati dalla bacheca: " + bacheca.iteratore().hasNext());
            return bacheca;
        } catch (Exception e) {
            System.out.println("Creazione di una nuova bacheca: " + e.getMessage());
            return new BachecaImplementa();
        }
    }



    /**
     * Salva lo stato corrente della bacheca su file.
     * Gestisce e logga eventuali errori durante il salvataggio.
     * 
     * @param bacheca la bacheca da salvare su file
     */
    private static void salvaBachecaSuFile(Bacheca bacheca) {
        try {
            FileUtil.salvaBacheca(bacheca, FILE_PATH);
            System.out.println("Bacheca salvata con successo");
        } catch (Exception e) {
            System.err.println("Errore nel salvataggio della bacheca: " + e.getMessage());
        }
    }
}