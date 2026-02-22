package upoUtil;

import java.io.*;
import upoBacheca.Bacheca;
import upoBacheca.BachecaImplementa;

/**
 * Classe di utilità per la gestione del salvataggio e caricamento della bacheca su file.
 * Implementa la persistenza dei dati attraverso la serializzazione Java.
 * 
 * Funzionalità principali:
 * - Serializzazione della bacheca su file
 * - Deserializzazione della bacheca da file
 * - Gestione delle eccezioni di I/O
 * 
 * @author DIABI, GOUAICHE
 */
public class FileUtil {
    
	 /**
     * Salva una bacheca su file attraverso la serializzazione.
     * Utilizza ObjectOutputStream per la serializzazione dell'oggetto.
     * La scrittura viene eseguita in modo sicuro utilizzando try-with-resources.
     * 
     * @param bacheca la bacheca da salvare
     * @param filePath il percorso del file dove salvare la bacheca
     * @throws IOException se si verifica un errore durante il salvataggio
     */
    public static void salvaBacheca(Bacheca bacheca, String filePath) throws IOException{
        try(ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(filePath))){
            oos.writeObject(bacheca);
        }
    }
    
    /**
     * Carica una bacheca da file attraverso la deserializzazione.
     * Utilizza ObjectInputStream per la deserializzazione dell'oggetto.
     * La lettura viene eseguita in modo sicuro utilizzando try-with-resources.
     * 
     * @param filePath il percorso del file da cui caricare la bacheca
     * @return la bacheca caricata dal file
     * @throws IOException se si verifica un errore durante la lettura
     * @throws ClassNotFoundException se la classe della bacheca non può essere trovata
     */
    public static Bacheca caricaBacheca(String filePath) throws IOException, ClassNotFoundException{
        try(ObjectInputStream ois= new ObjectInputStream(new FileInputStream(filePath))){
            return (BachecaImplementa) ois.readObject();
        }
    }
}