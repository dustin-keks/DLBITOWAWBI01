package com.example.demo.service;

import com.example.demo.dto.ProjektRequest;
import com.example.demo.dto.ProjektResponse;
import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Projekt;
import com.example.demo.entity.enums.AufgabeStatus;
import com.example.demo.entity.enums.ProjektStatus;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.ProjektRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjektService {
    private final ProjektRepository projektRepository;
    private final BenutzerRepository benutzerRepository;

    public List<ProjektResponse> getMeineProjekte(Benutzer benutzer) {
        return projektRepository.findByMitarbeitendeContaining(benutzer).stream()
                .map(this::buildResponse)
                .toList();
    }

    @Transactional
    public ProjektResponse projektAnlegen(ProjektRequest request, Benutzer ersteller) {
        Benutzer benutzer = benutzerRepository.findById(ersteller.getId())
                .orElseThrow(() -> new NotFoundException("Benutzer nicht gefunden"));

        Projekt projekt = new Projekt();
        projekt.setName(request.getName());
        projekt.setStatus(ProjektStatus.AKTIV);
        projekt.setMandant(benutzer.getMandant());

        projektRepository.save(projekt);

        benutzer.getProjekte().add(projekt);
        benutzerRepository.save(benutzer);

        return buildResponse(projekt);
    }

    @Transactional
    public ProjektResponse statusAendern(UUID id, ProjektStatus status) {
        Projekt projekt = projektRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projekt nicht gefunden"));
        projekt.setStatus(status);
        return buildResponse(projektRepository.save(projekt));
    }

    public ProjektResponse mitarbeiterZuordnen(UUID projektId, UUID benutzerId) {
        Projekt projekt = projektRepository.findById(projektId)
                .orElseThrow(() -> new NotFoundException("Projekt nicht gefunden"));
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new NotFoundException("Benutzer nicht gefunden"));

        projekt.getMitarbeitende().add(benutzer);
        benutzer.getProjekte().add(projekt);

        projektRepository.save(projekt);
        benutzerRepository.save(benutzer);

        return buildResponse(projekt);
    }

    private ProjektResponse buildResponse(Projekt projekt) {
        long alleAufgaben = projekt.getAufgaben().size();
        long erledigteAufgaben = projekt.getAufgaben().stream()
                .filter(aufgabe -> aufgabe.getStatus() == AufgabeStatus.ERLEDIGT)
                .count();
        double fortschritt = alleAufgaben == 0 ? 0.0 : (double) erledigteAufgaben / alleAufgaben * 100;

        return new ProjektResponse(
                projekt.getId(),
                projekt.getName(),
                projekt.getStatus(),
                fortschritt
        );
    }
}
