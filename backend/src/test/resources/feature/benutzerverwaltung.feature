# language: de
Funktionalität: Benutzerverwaltung durch Admin

  Szenario: Admin legt einen neuen Benutzer mit Rolle an
    Angenommen ein angemeldeter Admin
    Wenn der Admin einen Benutzer mit Name "Neuer Mitarbeiter", E-Mail-Adresse "neuer.mitarbeiter@beispiel.de", Passwort "geheim123" und der Rolle "MITARBEITER" anlegt
    Dann wird der Benutzer mit der Rolle "MITARBEITER" angelegt