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
import io.cucumber.java.de.Und;
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

import java.util.List;
import java.util.Map;

public class ProjektSichtbarkeitSteps {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MandantRepository mandantRepository;

    @Autowired
    private ProjektRepository projektRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Mandant mandant;
    private String token;
    private ResponseEntity<List> antwort;

    @Angenommen("ein Benutzer, der nur dem Projekt {string} zugeordnet ist")
    public void einBenutzerMitProjekt(String projektName) {
        mandant = new Mandant();
        mandant.setName("Test GmbH");
        mandantRepository.save(mandant);

        Benutzer benutzer = new Benutzer();
        benutzer.setMandant(mandant);
        benutzer.setName("Test Mitarbeiter");
        benutzer.setEmail("mitarbeiter@test-gmbh.de");
        benutzer.setPasswort(passwordEncoder.encode("geheim123"));
        benutzer.setRolle(BenutzerRolle.MITARBEITER);
        benutzerRepository.save(benutzer);

        Projekt projekt = new Projekt();
        projekt.setMandant(mandant);
        projekt.setName(projektName);
        projekt.setStatus(ProjektStatus.AKTIV);
        projekt.getMitarbeitende().add(benutzer);
        projektRepository.save(projekt);

        benutzer.getProjekte().add(projekt);
        benutzerRepository.save(benutzer);

        String loginUrl = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of(
                "email", "mitarbeiter@test-gmbh.de",
                "passwort", "geheim123"
        );
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, Map.class);
        token = (String) loginResponse.getBody().get("token");
    }

    @Und("ein weiteres Projekt {string}, dem der Benutzer nicht zugeordnet ist")
    public void einWeiteresProjektOhneZuordnung(String projektName) {
        Projekt projekt = new Projekt();
        projekt.setMandant(mandant);
        projekt.setName(projektName);
        projekt.setStatus(ProjektStatus.AKTIV);
        projektRepository.save(projekt);
    }

    @Wenn("der Benutzer seine Projekte abruft")
    public void derBenutzerSeineProjekteAbruft() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        String url  = "http://localhost:" + port + "/api/projekte";
        antwort = restTemplate.exchange(url, HttpMethod.GET, request, List.class);
    }

    @Dann("sieht der Benutzer nur das Projekt {string}")
    public void siehtDerBenutzerNurDasProjekt(String erwarteterName) {
        List<Map> projekte = antwort.getBody();
        Assertions.assertEquals(1, projekte.size());
        Assertions.assertEquals(erwarteterName, projekte.getFirst().get("name"));
    }
}
