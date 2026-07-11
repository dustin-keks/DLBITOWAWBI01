# language: de
Funktionalität: Sichtbarkeit von Projekten

  Szenario: Benutzer sieht nur die Projekte, denen er zugeordnet ist
    Angenommen ein Benutzer, der nur dem Projekt "Sichtbares Projekt" zugeordnet ist
    Und ein weiteres Projekt "Unsichtbares Projekt", dem der Benutzer nicht zugeordnet ist
    Wenn der Benutzer seine Projekte abruft
    Dann sieht der Benutzer nur das Projekt "Sichtbares Projekt"