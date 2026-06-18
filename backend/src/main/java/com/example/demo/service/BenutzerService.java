package com.example.demo.service;

import com.example.demo.dto.BenutzerRequest;
import com.example.demo.dto.BenutzerResponse;
import com.example.demo.entity.Benutzer;
import com.example.demo.repository.BenutzerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BenutzerService {
    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    public List<BenutzerResponse> getAlleBenutzer(Benutzer admin) {
        return benutzerRepository.findByMandant(admin.getMandant()).stream()
                .map(this::buildResponse)
                .toList();
    }

    public BenutzerResponse benutzerAnlegen(BenutzerRequest request, Benutzer admin) {
        Benutzer benutzer = new Benutzer();
        benutzer.setName(request.getName());
        benutzer.setEmail(request.getEmail());
        benutzer.setPasswort(passwordEncoder.encode(request.getPasswort()));
        benutzer.setRolle(request.getRolle());
        benutzer.setMandant(admin.getMandant());

        return buildResponse(benutzerRepository.save(benutzer));
    }

    public BenutzerResponse benutzerAktualisieren(UUID id, BenutzerRequest request) {
        Benutzer benutzer = benutzerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));

        benutzer.setName(request.getName());
        benutzer.setEmail(request.getEmail());
        benutzer.setRolle(request.getRolle());

        if (request.getPasswort() != null && !request.getPasswort().isBlank()) {
            benutzer.setPasswort(passwordEncoder.encode(request.getPasswort()));
        }

        return buildResponse(benutzerRepository.save(benutzer));
    }

    private BenutzerResponse buildResponse(Benutzer benutzer) {
        return new BenutzerResponse(
                benutzer.getId(),
                benutzer.getName(),
                benutzer.getEmail(),
                benutzer.getRolle()
        );
    }
}
