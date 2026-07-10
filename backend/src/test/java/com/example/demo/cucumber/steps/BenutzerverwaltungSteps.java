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
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

public class BenutzerverwaltungSteps {
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

    private String adminToken;
    private String angelegteEMail;

    @Angenommen("ein angemeldeter Admin")
    public void einAngemeldeterAdmin() {
        Mandant mandant = new Mandant();
        mandant.setName("Test GmbH");
        mandantRepository.save(mandant);

        Benutzer admin = new Benutzer();
        admin.setName("Neuer Mitarbeiter");
        admin.setEmail("neuer.mitarbeiter@beispiel.de");
        admin.setPasswort(passwordEncoder.encode("geheim123"));
        admin.setRolle(BenutzerRolle.MITARBEITER);
        admin.setMandant(mandant);
        benutzerRepository.save(admin);

        String loginUrl = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of(
                "email", "neuer.mitarbeiter@beispiel.de",
                "passwort", "geheim123"
        );
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, Map.class);
        adminToken = (String) loginResponse.getBody().get("token");
    }

    @Wenn("der Admin einen Benutzer mit Name {string}, E-Mail-Adresse {string}, Passwort {string} und der Rolle {string} anlegt")
    public void derAdminEinenBenutzerAnlegt(String name, String email, String passwort, String rolle) {
        angelegteEMail = email;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "name", name,
                "email", email,
                "passwort", passwort,
                "rolle", rolle
        );
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        String url  = "http://localhost:" + port + "/api/benutzer";
        restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
    }

    @Dann("wird der Benutzer mit der Rolle {string} angelegt")
    public void wirdDerBenutzerAnlegt(String erwarteteRolle) {
        Benutzer benutzer = benutzerRepository.findByEmail(angelegteEMail).orElseThrow();
        Assertions.assertEquals(BenutzerRolle.valueOf(erwarteteRolle), benutzer.getRolle());
    }
}
