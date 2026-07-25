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

import java.util.Map;

public class ProjektfortschrittSteps {
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
    private AufgabeRepository aufgabeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Mandant mandant;
    private Projekt projekt;
    private String mitarbeiterToken;
    private String projektleiterToken;
    private ResponseEntity<Map> projektAntwort;

    @Angenommen("ein Projekt {string} mit einer erledigten und einer offenen Aufgabe")
    public void einProjektMitEinerErledigtenUndEinerOffenenAufgabe(String projektName) {
        mandant = new Mandant();
        mandant.setName("Test Mandant");
        mandantRepository.save(mandant);

        projekt = new Projekt();
        projekt.setMandant(mandant);
        projekt.setName(projektName);
        projekt.setStatus(ProjektStatus.AKTIV);
        projektRepository.save(projekt);

        Aufgabe erledigtAufgabe = new Aufgabe();
        erledigtAufgabe.setTitel("Erledigte Aufgabe");
        erledigtAufgabe.setStatus(AufgabeStatus.ERLEDIGT);
        erledigtAufgabe.setProjekt(projekt);
        aufgabeRepository.save(erledigtAufgabe);

        Aufgabe offeneAufgabe = new Aufgabe();
        offeneAufgabe.setTitel("Offene Aufgabe");
        offeneAufgabe.setStatus(AufgabeStatus.OFFEN);
        offeneAufgabe.setProjekt(projekt);
        aufgabeRepository.save(offeneAufgabe);
    }

    @Und("ein Mitarbeiter, der diesem Projekt zugeordnet ist")
    public void einMitarbeiterDerDiesemProjektZugeordnetIst() {
        Benutzer mitarbeiter  = new Benutzer();
        mitarbeiter.setMandant(mandant);
        mitarbeiter.setName("Test Mitarbeiter");
        mitarbeiter.setEmail("mitarbeiter@test.de");
        mitarbeiter.setPasswort(passwordEncoder.encode("Abc123"));
        mitarbeiter.setRolle(BenutzerRolle.MITARBEITER);
        benutzerRepository.save(mitarbeiter);

        projekt.getMitarbeitende().add(mitarbeiter);
        mitarbeiter.getProjekte().add(projekt);
        projektRepository.save(projekt);
        benutzerRepository.save(mitarbeiter);

        mitarbeiterToken = login("mitarbeiter@test.de", "Abc123");
    }

    @Und("ein Projektleiter, der diesem Projekt zugeordnet ist")
    public void einProjektleiterDerDiesemProjektZugeordnetIst() {
        Benutzer projektleiter  = new Benutzer();
        projektleiter.setMandant(mandant);
        projektleiter.setName("Test Projektleiter");
        projektleiter.setEmail("projektleiter@test.de");
        projektleiter.setPasswort(passwordEncoder.encode("Def456"));
        projektleiter.setRolle(BenutzerRolle.PROJEKTLEITER);
        benutzerRepository.save(projektleiter);

        projekt.getMitarbeitende().add(projektleiter);
        projektleiter.getProjekte().add(projekt);
        projektRepository.save(projekt);
        benutzerRepository.save(projektleiter);

        projektleiterToken = login("projektleiter@test.de", "Def456");
    }

    @Wenn("der Mitarbeiter das Projekt abruft")
    public void derMitarbeiterDasProjektAbruft() {
        projektAntwort = projektAbrufen(mitarbeiterToken);
    }

    @Dann("sieht der Mitarbeiter keinen Projektfortschritt")
    public void siehtDerMitarbeiterKeinenProjektfortschritt() {
        Assertions.assertNull(projektAntwort.getBody().get("fortschritt"));
    }

    @Wenn("der Projektleiter das Projekt abruft")
    public void derProjektleiterDasProjektAbruft() {
        projektAntwort = projektAbrufen(projektleiterToken);
    }

    @Dann("sieht der Projektleiter einen Projektfortschritt von {double} Prozent")
    public void siehtDerProjektleiterEinenProjektfortschrittVonProzent(double erwarteterProzentsatz) {
        Assertions.assertEquals(erwarteterProzentsatz, projektAntwort.getBody().get("fortschritt"));
    }

    private String login(String email, String passwort) {
        String url = "http://localhost:" + port + "/api/auth/login";
        Map<String, String> loginRequest = Map.of("email", email, "passwort", passwort);
        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(url, loginRequest, Map.class);
        return (String) loginResponse.getBody().get("token");
    }

    private ResponseEntity<Map> projektAbrufen(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        String url = "http://localhost:" + port + "/api/projekte/" + projekt.getId();
        return restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
    }
}
