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

import java.sql.SQLOutput;

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

        System.out.println("Seed Daten angelegt.");
        System.out.println("Mandant: " + mandant.getName());
        System.out.println("Benutzer (Admin): " + admin.getEmail());
    }
}
