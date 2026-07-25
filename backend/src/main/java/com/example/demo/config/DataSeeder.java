package com.example.demo.config;

import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import com.example.demo.entity.enums.BenutzerRolle;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.MandantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final MandantRepository mandantRepository;
    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (mandantRepository.count() > 0) {
            return;
        }

        Mandant mandant = new Mandant();
        mandant.setName("Beispiel GmbH");
        mandantRepository.save(mandant);

        Benutzer admin = new Benutzer();
        admin.setName("admin");
        admin.setEmail("admin@beispiel.de");
        admin.setPasswort(passwordEncoder.encode("admin123"));
        admin.setRolle(BenutzerRolle.ADMIN);
        admin.setMandant(mandant);
        benutzerRepository.save(admin);

        Mandant mandant2 = new Mandant();
        mandant2.setName("Schule für Hexerei und Zauberei");
        mandantRepository.save(mandant2);

        Benutzer admin2 = new Benutzer();
        admin2.setName("dumbledore");
        admin2.setEmail("dumbledore@hogwarts.de");
        admin2.setPasswort(passwordEncoder.encode("Schokofrosch"));
        admin2.setRolle(BenutzerRolle.ADMIN);
        admin2.setMandant(mandant2);
        benutzerRepository.save(admin2);

        System.out.println("Seed Daten angelegt.");
        System.out.println("1. Mandant: " + mandant.getName());
        System.out.println("Benutzer (Admin): " + admin.getEmail());
        System.out.println("2. Mandant: " + mandant2.getName());
        System.out.println("Benutzer (Admin): " + admin2.getEmail());
    }
}
