# language: de
Funktionalität: Sichtbarkeit des Projektfortschritts

  Szenario: Nur Projektleiter und Admin sehen den echten Projektfortschritt
    Angenommen ein Projekt "Fortschritts-Test" mit einer erledigten und einer offenen Aufgabe
    Und ein Mitarbeiter, der diesem Projekt zugeordnet ist
    Und ein Projektleiter, der diesem Projekt zugeordnet ist
    Wenn der Mitarbeiter das Projekt abruft
    Dann sieht der Mitarbeiter keinen Projektfortschritt
    Wenn der Projektleiter das Projekt abruft
    Dann sieht der Projektleiter einen Projektfortschritt von 50 Prozent
