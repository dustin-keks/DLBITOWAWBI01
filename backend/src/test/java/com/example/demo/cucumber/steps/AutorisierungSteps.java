package com.example.demo.cucumber.steps;

import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import com.example.demo.entity.Projekt;
import com.example.demo.entity.enums.BenutzerRolle;
import com.example.demo.entity.enums.ProjektStatus;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.MandantRepository;
import com.example.demo.repository.ProjektRepository;
import io.cucumber.java.PendingException;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Und;
import io.cucumber.java.de.Wenn;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.UUID;

public class AutorisierungSteps {
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
    private PasswordEncoder passwordEncoder;

    private String token;
    private ResponseEntity<Map> antwort;
    private UUID fremdesProjektId;

    @Angenommen("ein angemeldeter Mitarbeitender ohne Admin-Rechte")
    public void einAngemeldeterMitarbeitender() {
        Mandant mandant = new Mandant();
        mandant.setName("Test GmbH");
        mandantRepository.save(mandant);

        Benutzer benutzer = new Benutzer();
        benutzer.setName("Test Mitarbeiter");
        benutzer.setEmail("mitarbeiter.autorisierung@beispiel.de");
        benutzer.setPasswort(passwordEncoder.encode("geheim123"));
        benutzer.setRolle(BenutzerRolle.MITARBEITER);
        benutzer.setMandant(mandant);
        benutzerRepository.save(benutzer);

        String loginUrl = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of(
                "email", "mitarbeiter.autorisierung@beispiel.de",
                "passwort", "geheim123"
        );
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, Map.class);
        token = (String) loginResponse.getBody().get("token");
    }

    @Wenn("der Mitarbeitende versucht, die Benutzerliste abzurufen")
    public void dieBenutzerlisteAbrufen() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        String url = "http://localhost:" + port + "/api/benutzer";
        antwort = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
    }

    @Dann("wird der Zugriff mit Status {int} verweigert")
    public void wirdDerZugriffVerweigert(int erwarteterStatus) {
        Assertions.assertEquals(erwarteterStatus, antwort.getStatusCode().value());
    }

    @Angenommen("ein Projekt {string} im Mandanten {string}")
    public void einProjektImMandanten(String projektName, String mandantName) {
        Mandant fremderMandant = new Mandant();
        fremderMandant.setName(mandantName);
        mandantRepository.save(fremderMandant);

        Projekt fremdesProjekt = new Projekt();
        fremdesProjekt.setMandant(fremderMandant);
        fremdesProjekt.setName(projektName);
        fremdesProjekt.setStatus(ProjektStatus.AKTIV);
        projektRepository.save(fremdesProjekt);

        fremdesProjektId = fremdesProjekt.getId();
    }

    @Und("ein angemeldeter Projektleiter in einem eigenen Mandanten")
    public void einAngemeldeterProjektleiterInEinemEigenenMandanten() {
        Mandant eigenerMandant = new Mandant();
        eigenerMandant.setName("Eigene GmbH");
        mandantRepository.save(eigenerMandant);

        Benutzer fremderProjektleiter = new Benutzer();
        fremderProjektleiter.setMandant(eigenerMandant);
        fremderProjektleiter.setName("Fremder Projektleiter");
        fremderProjektleiter.setEmail("projektleiter@fremde-gmbh.de");
        fremderProjektleiter.setPasswort(passwordEncoder.encode("Hallo-Welt-123"));
        fremderProjektleiter.setRolle(BenutzerRolle.PROJEKTLEITER);
        benutzerRepository.save(fremderProjektleiter);

        String loginUrl = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of(
                "email", "projektleiter@fremde-gmbh.de",
                "passwort", "Hallo-Welt-123"
        );
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, Map.class);
        token = (String) loginResponse.getBody().get("token");
    }

    @Wenn("der Projektleiter versucht, das fremde Projekt zu archivieren")
    public void derProjektleiterVersuchtDasFremdeProjektZuArchivieren() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("status", "ARCHIVIERT"), headers);

        String url = "http://localhost:" + port + "/api/projekte/" + fremdesProjektId + "/status";
        antwort = restTemplate.exchange(url, HttpMethod.PATCH, request, Map.class);
    }
}
