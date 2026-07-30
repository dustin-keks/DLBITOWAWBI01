# language: de
Funktionalität: Rollenbasierte Autorisierung

  Szenario: Mitarbeitender darf keine Benutzerliste abrufen
    Angenommen ein angemeldeter Mitarbeitender ohne Admin-Rechte
    Wenn der Mitarbeitende versucht, die Benutzerliste abzurufen
    Dann wird der Zugriff mit Status 403 verweigert

  Szenario: Projektleiter darf kein Projekt eines fremden Mandanten archivieren
    Angenommen ein Projekt "Fremdes Projekt" im Mandanten "Andere GmbH"
    Und ein angemeldeter Projektleiter in einem eigenen Mandanten
    Wenn der Projektleiter versucht, das fremde Projekt zu archivieren
    Dann wird der Zugriff mit Status 403 verweigert
