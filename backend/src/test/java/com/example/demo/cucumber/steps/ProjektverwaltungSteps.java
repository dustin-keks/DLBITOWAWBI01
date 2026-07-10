package com.example.demo.cucumber.steps;

import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import com.example.demo.entity.Projekt;
import com.example.demo.entity.enums.BenutzerRolle;
import com.example.demo.entity.enums.ProjektStatus;
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

public class ProjektverwaltungSteps {
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
    private UUID projektId;

    @Angenommen("ein angemeldeter Projektleiter")
    public void einAngemeldeterProjektleiter() {
        Mandant mandant = new Mandant();
        mandant.setName("Test GmbH");
        mandantRepository.save(mandant);

        Benutzer projektleiter = new Benutzer();
        projektleiter.setName("Test Projektleiter");
        projektleiter.setEmail("projektleiter@test-gmbh.de");
        projektleiter.setPasswort(passwordEncoder.encode("geheimes_passwort"));
        projektleiter.setRolle(BenutzerRolle.PROJEKTLEITER);
        projektleiter.setMandant(mandant);
        benutzerRepository.save(projektleiter);

        String loginUrl = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of(
                "email", "projektleiter@test-gmbh.de",
                "passwort", "geheimes_passwort"
        );
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, Map.class);
        token = (String) loginResponse.getBody().get("token");
    }

    @Wenn("der Projektleiter ein Projekt mit dem Namen {string} anlegt")
    public void derProjektleiterEinProjektAnlegt(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("name", name), headers);

        String url =  "http://localhost:" + port + "/api/projekte";
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        projektId = UUID.fromString((String) response.getBody().get("id"));
    }

    @Dann("existiert ein Projekt mit dem Namen {string} und dem Status {string}")
    public void existiertEinProjekt(String erwarteterName, String erwarteterStatus) {
        Projekt projekt = projektRepository.findById(projektId).orElseThrow();
        Assertions.assertEquals(erwarteterName, projekt.getName());
        Assertions.assertEquals(ProjektStatus.valueOf(erwarteterStatus), projekt.getStatus());
    }

    @Wenn("der Projektleiter das Projekt in {string} umbenennt")
    public void derProjektleiterDasProjektUmbenennt(String neuerName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("name", neuerName), headers);

        String url =  "http://localhost:" + port + "/api/projekte/" + projektId;
        restTemplate.exchange(url, HttpMethod.PUT, request, Map.class);
    }

    @Dann("hat das Projekt den Namen {string}")
    public void hatDasProjektDenNamen(String erwarteterName) {
        Projekt projekt = projektRepository.findById(projektId).orElseThrow();
        Assertions.assertEquals(erwarteterName, projekt.getName());
    }

    @Wenn("der Projektleiter das Projekt archiviert")
    public void derProjektleiterDasProjektArchiviert() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("status", "ARCHIVIERT"), headers);

        String url =  "http://localhost:" + port + "/api/projekte/" + projektId + "/status";
        restTemplate.exchange(url, HttpMethod.PATCH, request, Map.class);
    }

    @Dann("hat das Projekt den Status {string}")
    public void hatDasProjektDenStatus(String erwarteterStatus) {
        Projekt projekt = projektRepository.findById(projektId).orElseThrow();
        Assertions.assertEquals(ProjektStatus.valueOf(erwarteterStatus), projekt.getStatus());
    }
}
