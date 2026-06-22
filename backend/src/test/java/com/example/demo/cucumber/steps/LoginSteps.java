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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

public class LoginSteps {

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

    private ResponseEntity<Map> antwort;

    @Angenommen("ein registrierter Benutzer mit der Email {string} und dem Passwort {string}")
    public void einRegistrierterBenutzer(String email, String passwort) {
        Mandant mandant = new Mandant();
        mandant.setName("Test GmbH");
        mandantRepository.save(mandant);

        Benutzer benutzer = new Benutzer();
        benutzer.setName("Test Benutzer");
        benutzer.setEmail(email);
        benutzer.setPasswort(passwordEncoder.encode(passwort));
        benutzer.setRolle(BenutzerRolle.ADMIN);
        benutzer.setMandant(mandant);
        benutzerRepository.save(benutzer);
    }

    @Wenn("sich der Benutzer mit Email {string} und Passwort {string} anmeldet")
    public void derBenutzerMeldetSichAn(String email, String passwort) {
        String url = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> request = Map.of("email", email, "passwort", passwort);
        antwort = restTemplate.postForEntity(url, request, Map.class);
    }

    @Dann("ist die Antwort erfolgreich und enthält einen Token")
    public void istDieAntwortErfolgreich() {
        Assertions.assertEquals(200, antwort.getStatusCode().value());
        Assertions.assertNotNull(antwort.getBody());
        Assertions.assertTrue(antwort.getBody().containsKey("token"));
    }

    @Dann("schlägt die Anmeldung mit Status {int} fehl")
    public void schlaegtDieAnmeldungFehl(int erwarteterStatus) {
        Assertions.assertEquals(erwarteterStatus, antwort.getStatusCode().value());
    }
}