# DLBITOWAWBI01
Programmierung von Web-Anwendungen - webbasierte betriebliche Informationssysteme

## Voraussetzungen
- JDK 25
- Node.js mit npm
- lokale MySQL Instanz

## Backend

### 1. Datenbank erstellen
```sql
CREATE DATABASE dlbitowawbi01;
```

### 2. Datei `backend/src/main/resources/application-local.properties` anlegen:
```properties
DB_USERNAME=<username>
DB_PASSWORD=<password>
JWT_SECRET=<secret>
```

### 3. Backend starten
```bash
cd .\backend\
.\gradlew bootRun
```
Das Backend läuft anschließend unter `http://localhost:8080`.
Die API-Dokumentation läuft unter `http://localhost:8080/swagger-ui/index.html`.

### 4. Backend Tests ausführen
```bash
.\gradlew test
```

## Frontend

### 1. Frontend starten
```bash
cd .\frontend\
npm install
npm start
```
Das Frontend läuft anschließend unter `http://localhost:4200`.

### 2. Frontend Tests ausführen
```bash
npm test
```

## Demo-Zugangsdaten
Beim ersten Start des Backends legt der `DataSeeder` automatisch zwei Mandanten mit Admin-Benutzer an:

| Mandant                          | E-Mail-Adresse         | Passwort      |
|----------------------------------|------------------------|---------------|
| Beispiel GmbH                    | admin@beispiel.de      | admin123      |
| Schule für Hexerei und Zauberei  | dumbledore@hogwarts.de | Schokofrosch  |

Automatisch werden keine Projekte oder Aufgaben angelegt.
Nach dem ersten Login ist die Projektliste entsprechend leer.
Alternativ kann die Datei `/dump.sql` mit Beispieldaten verwendet werden.
