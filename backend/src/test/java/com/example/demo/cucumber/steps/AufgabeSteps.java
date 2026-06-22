package com.example.demo.cucumber.steps;

import com.example.demo.entity.Aufgabe;
import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import com.example.demo.entity.Projekt;
import com.example.demo.entity.enums.AufgabeStatus;
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

public class AufgabeSteps {
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

    private String email;
    private String passwort;
    private UUID projektId;
    private UUID aufgabeId;

    @Angenommen("ein Mitarbeitender mit der Email {string} und dem Passwort {string}")
    public void einMitarbeitender(String email, String passwort) {
        this.email = email;
        this.passwort = passwort;

        Mandant mandant = new Mandant();
        mandant.setName("Test GmbH");
        mandantRepository.save(mandant);

        Benutzer benutzer = new Benutzer();
        benutzer.setName("Test Mitarbeiter");
        benutzer.setEmail(email);
        benutzer.setPasswort(passwordEncoder.encode(passwort));
        benutzer.setRolle(BenutzerRolle.MITARBEITER);
        benutzer.setMandant(mandant);
        benutzerRepository.save(benutzer);
    }

    @Angenommen("eine Aufgabe {string} mit dem Status {string}")
    public void eineAufgabe(String titel, String status) {
        Benutzer benutzer = benutzerRepository.findByEmail(email).orElseThrow();

        Projekt projekt = new Projekt();
        projekt.setName("Testprojekt");
        projekt.setStatus(ProjektStatus.AKTIV);
        projekt.setMandant(benutzer.getMandant());
        projektRepository.save(projekt);

        Aufgabe aufgabe = new Aufgabe();
        aufgabe.setTitel(titel);
        aufgabe.setStatus(AufgabeStatus.valueOf(status));
        aufgabe.setProjekt(projekt);
        aufgabeRepository.save(aufgabe);

        this.projektId = projekt.getId();
        this.aufgabeId = aufgabe.getId();
    }

    @Wenn("der Mitarbeitende den Status der Aufgabe auf {string} ändert")
    public void statusAendern(String neuerStatus) {
        String loginUrl = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of("email", email, "passwort", passwort);
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, Map.class);
        String token = (String) loginResponse.getBody().get("token");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> statusRequest = Map.of("status", neuerStatus);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(statusRequest, headers);

        String url = "http://localhost:" + port + "/api/projekte/" + projektId + "/aufgaben/" + aufgabeId + "/status";
        restTemplate.exchange(url, HttpMethod.PUT, request, Map.class);
    }

    @Dann("hat die Aufgabe den Status {string}")
    public void hatDieAufgabeDenStatus(String erwarteterStatus) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId).orElseThrow();
        Assertions.assertEquals(AufgabeStatus.valueOf(erwarteterStatus), aufgabe.getStatus());
    }
}
