package com.example.demo.service;

import com.example.demo.dto.AufgabeRequest;
import com.example.demo.dto.AufgabeResponse;
import com.example.demo.dto.AufgabeStatusRequest;
import com.example.demo.dto.BenutzerResponse;
import com.example.demo.entity.Aufgabe;
import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Projekt;
import com.example.demo.entity.enums.AufgabeStatus;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.AufgabeRepository;
import com.example.demo.repository.ProjektRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AufgabeService {
    private final AufgabeRepository aufgabeRepository;
    private final ProjektRepository projektRepository;

    public List<AufgabeResponse> getAufgabenFuerProjekt(UUID projektId, Benutzer benutzer) {
        Projekt projekt = projektRepository.findById(projektId)
                .orElseThrow(() -> new NotFoundException("Projekt nicht gefunden"));

        pruefeZugriff(projekt, benutzer);

        return aufgabeRepository.findByProjektId(projektId).stream()
                .map(this::buildResponse)
                .toList();
    }

    public AufgabeResponse aufgabeAnlegen(UUID projektId, AufgabeRequest request, Benutzer benutzer) {
        Projekt projekt = projektRepository.findById(projektId)
                .orElseThrow(() -> new NotFoundException("Projekt nicht gefunden"));

        pruefeZugriff(projekt, benutzer);

        Aufgabe aufgabe = new Aufgabe();
        aufgabe.setTitel(request.getTitel());
        aufgabe.setBeschreibung(request.getBeschreibung());
        aufgabe.setStatus(AufgabeStatus.OFFEN);
        aufgabe.setProjekt(projekt);

        return buildResponse(aufgabeRepository.save(aufgabe));
    }

    public AufgabeResponse statusAendern(UUID aufgabeId, AufgabeStatusRequest request, Benutzer benutzer) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId)
                .orElseThrow(() -> new NotFoundException("Aufgabe nicht gefunden"));

        pruefeZugriff(aufgabe.getProjekt(), benutzer);

        aufgabe.setStatus(request.getStatus());

        return buildResponse(aufgabeRepository.save(aufgabe));
    }

    public AufgabeResponse aufgabeAktualisieren(UUID aufgabeId, AufgabeRequest request, Benutzer benutzer) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId)
                .orElseThrow(() -> new NotFoundException("Aufgabe nicht gefunden"));

        pruefeZugriff(aufgabe.getProjekt(), benutzer);

        aufgabe.setTitel(request.getTitel());
        aufgabe.setBeschreibung(request.getBeschreibung());

        return buildResponse(aufgabeRepository.save(aufgabe));
    }

    public void aufgabeLoeschen(UUID aufgabeId, Benutzer benutzer) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId)
                .orElseThrow(() -> new NotFoundException("Aufgabe nicht gefunden"));

        pruefeZugriff(aufgabe.getProjekt(), benutzer);

        aufgabeRepository.delete(aufgabe);
    }

    public AufgabeResponse aufgabeZuweisen(UUID aufgabeId, UUID benutzerId, Benutzer benutzer) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId)
                .orElseThrow(() -> new NotFoundException("Aufgabe nicht gefunden"));

        pruefeZugriff(aufgabe.getProjekt(), benutzer);

        if (benutzerId == null) {
            aufgabe.setZugewiesenerBenutzer(null);
        } else {
            Benutzer neuerBenutzer = aufgabe.getProjekt().getMitarbeitende().stream()
                    .filter(mitglied -> mitglied.getId().equals(benutzerId))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Der Benutzer ist kein Mitglied dieses Projekts."));

            aufgabe.setZugewiesenerBenutzer(neuerBenutzer);
        }

        return buildResponse(aufgabeRepository.save(aufgabe));
    }

    private void pruefeZugriff(Projekt projekt, Benutzer benutzer) {
        boolean istMitglied = projekt.getMitarbeitende().stream()
                .anyMatch(mitglied -> mitglied.getId().equals(benutzer.getId()));
        if (!istMitglied) {
            throw new AccessDeniedException("Du hast keinen Zugriff auf dieses Projekt.");
        }
    }

    private AufgabeResponse buildResponse(Aufgabe aufgabe) {
        BenutzerResponse zugewiesenerBenutzer = aufgabe.getZugewiesenerBenutzer() == null ? null : new BenutzerResponse(
                aufgabe.getZugewiesenerBenutzer().getId(),
                aufgabe.getZugewiesenerBenutzer().getName(),
                aufgabe.getZugewiesenerBenutzer().getEmail(),
                aufgabe.getZugewiesenerBenutzer().getRolle()
        );

        return new AufgabeResponse(
                aufgabe.getId(),
                aufgabe.getTitel(),
                aufgabe.getBeschreibung(),
                aufgabe.getStatus(),
                zugewiesenerBenutzer
        );
    }
}
