# language: de
Funktionalität: Aufgabenstatus ändern

  Szenario: Mitarbeitender ändert den Status einer Aufgabe auf erledigt
    Angenommen ein Mitarbeitender mit der Email "mitarbeiter.test@beispiel.de" und dem Passwort "geheim123"
    Und eine Aufgabe "Datenbank entwerfen" mit dem Status "OFFEN"
    Wenn der Mitarbeitende den Status der Aufgabe auf "ERLEDIGT" ändert
    Dann hat die Aufgabe den Status "ERLEDIGT"
