# language: de
Funktionalität: Rollenbasierte Autorisierung

  Szenario: Mitarbeitender darf keine Benutzerliste abrufen
    Angenommen ein angemeldeter Mitarbeitender ohne Admin-Rechte
    Wenn der Mitarbeitende versucht, die Benutzerliste abzurufen
    Dann wird der Zugriff mit Status 403 verweigert
