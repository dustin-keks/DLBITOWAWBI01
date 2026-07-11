package com.example.demo.cucumber.steps;

import com.example.demo.entity.Aufgabe;
import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import com.example.demo.entity.Projekt;
import com.example.demo.entity.enums.BenutzerRolle;
import com.example.demo.entity.enums.ProjektStatus;
import com.example.demo.repository.AufgabeRepository;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.MandantRepository;
import com.example.demo.repository.ProjektRepository;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.UUID;

public class AufgabenverwaltungSteps {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MandantRepository mandantRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private ProjektRepository projektRepository;

    @Autowired
    private AufgabeRepository aufgabeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String token;
    private UUID projektId;
    private UUID aufgabeId;

    @Angenommen("ein Mitarbeiter, der einem Projekt zugeordnet ist")
    public void einMitarbeiterMitProjekt() {
        Mandant mandant = new Mandant();
        mandant.setName("Beispiel GmbH");
        mandantRepository.save(mandant);

        Benutzer benutzer = new Benutzer();
        benutzer.setMandant(mandant);
        benutzer.setName("Max Mustermann");
        benutzer.setEmail("aufgabenverwaltung@beispiel-gmbh.de");
        benutzer.setPasswort(passwordEncoder.encode("Streng-Geheim-987"));
        benutzer.setRolle(BenutzerRolle.MITARBEITER);
        benutzerRepository.save(benutzer);

        Projekt projekt = new Projekt();
        projekt.setMandant(mandant);
        projekt.setName("Ein neues Projekt");
        projekt.setStatus(ProjektStatus.AKTIV);
        projekt.getMitarbeitende().add(benutzer);
        projektRepository.save(projekt);
        projektId = projekt.getId();

        benutzer.getProjekte().add(projekt);
        benutzerRepository.save(benutzer);

        String loginUrl = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of(
                "email", "aufgabenverwaltung@beispiel-gmbh.de",
                "passwort", "Streng-Geheim-987"
        );
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, Map.class);
        token = (String) loginResponse.getBody().get("token");
    }

    @Wenn("der Mitarbeiter eine Aufgabe mit dem Titel {string} und der Beschreibung {string} anlegt")
    public void derMitarbeitereineAufgabeAnlegt(String titel, String beschreibung) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "titel", titel,
                "beschreibung", beschreibung
        );
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        String url = "http://localhost:" + port + "/api/projekte/" + projektId + "/aufgaben";
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        aufgabeId = UUID.fromString((String) response.getBody().get("id"));
    }

    @Dann("existiert eine Aufgabe mit dem Titel {string} und der Beschreibung {string}")
    public void existiertEineAufgabe(String erwarteterTitel, String erwarteteBeschreibung) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId).orElseThrow();
        Assertions.assertEquals(erwarteterTitel, aufgabe.getTitel());
        Assertions.assertEquals(erwarteteBeschreibung, aufgabe.getBeschreibung());
    }

    @Wenn("der Mitarbeiter die Aufgabe aktualisiert in den Titel {string} und die Beschreibung {string} aktualisiert")
    public void derMitarbeiterDieAufgabeAktualisiert(String titel, String beschreibung) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "titel", titel,
                "beschreibung", beschreibung
        );
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        String url = "http://localhost:" + port + "/api/projekte/" + projektId + "/aufgaben/" + aufgabeId;
        restTemplate.exchange(url, HttpMethod.PUT, request, Map.class);
    }

    @Dann("hat die Aufgabe den Titel {string} und die Beschreibung {string}")
    public void hatDieAufgabeDenTitel(String erwarteterTitel, String erwarteteBeschreibung) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId).orElseThrow();
        Assertions.assertEquals(erwarteterTitel, aufgabe.getTitel());
        Assertions.assertEquals(erwarteteBeschreibung, aufgabe.getBeschreibung());
    }
}
