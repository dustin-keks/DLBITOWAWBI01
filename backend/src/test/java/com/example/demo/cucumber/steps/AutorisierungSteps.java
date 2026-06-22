package com.example.demo.cucumber.steps;

import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import com.example.demo.entity.enums.BenutzerRolle;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.MandantRepository;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

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
    private PasswordEncoder passwordEncoder;

    private String token;
    private ResponseEntity<Map> antwort;

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
}
