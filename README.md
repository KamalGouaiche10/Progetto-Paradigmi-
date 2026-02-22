# Progetto Paradigmi — Gestione Bacheca

Descrizione
-----------
Applicazione Java per la gestione di una bacheca di annunci (vendita e acquisto). Il progetto fornisce:

- Modello di dominio per annunci e utenti
- Interfacce CLI e GUI per interazione con l'utente
- Utility per persistenza su file e confronti
- Suite di test JUnit per verifiche automatiche

Caratteristiche principali
-------------------------
- Creazione, ricerca e rimozione di annunci
- Filtri per tipologia (vendita/acquisto) e ordinamento per prezzo
- Salvataggio/caricamento tramite utilità di file

Esecuzione locale
-----------------
- Eseguire `Main` dal proprio IDE scegliendo la classe `upoMain.Main`, oppure generare il jar e lanciarlo se il packaging è configurato.

Struttura del progetto
----------------------
- src/upoBacheca/: classi del dominio (`Annuncio`, `Bacheca`, `Utente`, ...)
- src/UpoCli/: interfaccia a riga di comando (`BachecaCli`)
- src/UpoGui/: interfaccia grafica (`BachecaGui`)
- src/upoMain/: entrypoint (`Main.java`)
- src/UpoTest/: test JUnit
- src/upoUtil/: utilità e comparatori

Esempio d'uso (CLI)
-------------------
1. Compilare il progetto
2. Avviare `Main` dall'IDE o lanciare il jar prodotto
3. Seguire le istruzioni a schermo fornite dalla CLI


Autori
------
- Gouaiche Kamal
- Diabi Mohamed Zakaria
