# language: de
Funktionalität: Projektverwaltung durch Projektleiter

  Szenario: Projektleiter legt ein Projekt an, bearbeitet es und archiviert es
    Angenommen ein angemeldeter Projektleiter
    Wenn der Projektleiter ein Projekt mit dem Namen "Website Relaunch" anlegt
    Dann existiert ein Projekt mit dem Namen "Website Relaunch" und dem Status "AKTIV"
    Wenn der Projektleiter das Projekt in "Website Relaunch 2.0" umbenennt
    Dann hat das Projekt den Namen "Website Relaunch 2.0"
    Wenn der Projektleiter das Projekt archiviert
    Dann hat das Projekt den Status "ARCHIVIERT"