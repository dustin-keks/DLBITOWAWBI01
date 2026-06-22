# language: de
Funktionalität: Login

  Szenario: Erfolgreicher Login mit korrekten Zugangsdaten
    Angenommen ein registrierter Benutzer mit der Email "admin.test@beispiel.de" und dem Passwort "geheim123"
    Wenn sich der Benutzer mit Email "admin.test@beispiel.de" und Passwort "geheim123" anmeldet
    Dann ist die Antwort erfolgreich und enthält einen Token

  Szenario: Fehlgeschlagener Login mit falschem Passwort
    Angenommen ein registrierter Benutzer mit der Email "admin.test@beispiel.de" und dem Passwort "geheim123"
    Wenn sich der Benutzer mit Email "admin.test@beispiel.de" und Passwort "falschesPasswort" anmeldet
    Dann schlägt die Anmeldung mit Status 401 fehl
