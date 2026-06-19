package com.example.demo.service;

import com.example.demo.dto.AufgabeRequest;
import com.example.demo.dto.AufgabeResponse;
import com.example.demo.dto.AufgabeStatusRequest;
import com.example.demo.entity.Aufgabe;
import com.example.demo.entity.Projekt;
import com.example.demo.entity.enums.AufgabeStatus;
import com.example.demo.repository.AufgabeRepository;
import com.example.demo.repository.ProjektRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AufgabeService {
    private final AufgabeRepository aufgabeRepository;
    private final ProjektRepository projektRepository;

    public List<AufgabeResponse> getAufgabenFuerProjekt(UUID projektId) {
        return aufgabeRepository.findByProjektId(projektId).stream()
                .map(this::buildResponse)
                .toList();
    }

    public AufgabeResponse aufgabeAnlegen(UUID projektId, AufgabeRequest request) {
        Projekt projekt = projektRepository.findById(projektId)
                .orElseThrow(() -> new RuntimeException("Projekt nicht gefunden"));

        Aufgabe aufgabe = new Aufgabe();
        aufgabe.setTitel(request.getTitel());
        aufgabe.setStatus(AufgabeStatus.OFFEN);
        aufgabe.setProjekt(projekt);

        return buildResponse(aufgabeRepository.save(aufgabe));
    }

    public AufgabeResponse statusAendern(UUID aufgabeId, AufgabeStatusRequest request) {
        Aufgabe aufgabe = aufgabeRepository.findById(aufgabeId)
                .orElseThrow(() -> new RuntimeException("Aufgabe nicht gefunden"));

        aufgabe.setStatus(request.getStatus());

        return buildResponse(aufgabeRepository.save(aufgabe));
    }

    private AufgabeResponse buildResponse(Aufgabe aufgabe) {
        return new AufgabeResponse(
                aufgabe.getId(),
                aufgabe.getTitel(),
                aufgabe.getStatus()
        );
    }
}
