# language: de
Funktionalität: Aufgabenverwaltung durch Mitarbeitende

  Szenario: Mitarbeitender legt eine Aufgabe an und bearbeitet sie
    Angenommen ein Mitarbeiter, der einem Projekt zugeordnet ist
    Wenn der Mitarbeiter eine Aufgabe mit dem Titel "Datenbank entwerfen" und der Beschreibung "ER-Diagramm erstellen" anlegt
    Dann existiert eine Aufgabe mit dem Titel "Datenbank entwerfen" und der Beschreibung "ER-Diagramm erstellen"
    Wenn der Mitarbeiter die Aufgabe aktualisiert in den Titel "Datenbank entwerfen (überarbeitet)" und die Beschreibung "ER-Diagramm final" aktualisiert
    Dann hat die Aufgabe den Titel "Datenbank entwerfen (überarbeitet)" und die Beschreibung "ER-Diagramm final"