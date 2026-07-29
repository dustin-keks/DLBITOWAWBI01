package com.example.demo.controller;

import com.example.demo.dto.AufgabeRequest;
import com.example.demo.dto.AufgabeResponse;
import com.example.demo.dto.AufgabeStatusRequest;
import com.example.demo.dto.AufgabeZuweisenRequest;
import com.example.demo.entity.Benutzer;
import com.example.demo.service.AufgabeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projekte/{projektId}/aufgaben")
@RequiredArgsConstructor
public class AufgabeController {
    private final AufgabeService aufgabeService;

    @GetMapping
    public ResponseEntity<List<AufgabeResponse>> getAufgaben(@PathVariable UUID projektId,
                                                             @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(aufgabeService.getAufgabenFuerProjekt(projektId, benutzer));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER', 'MITARBEITER')")
    public ResponseEntity<AufgabeResponse> aufgabeAnlegen(@PathVariable UUID projektId,
                                                          @RequestBody AufgabeRequest request,
                                                          @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aufgabeService.aufgabeAnlegen(projektId, request, benutzer));
    }

    @PutMapping("/{aufgabeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER', 'MITARBEITER')")
    public ResponseEntity<AufgabeResponse> aufgabeAktualisieren(@PathVariable UUID aufgabeId,
                                                                @RequestBody AufgabeRequest request,
                                                                @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(aufgabeService.aufgabeAktualisieren(aufgabeId, request, benutzer));
    }

    @DeleteMapping("/{aufgabeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER', 'MITARBEITER')")
    public ResponseEntity<Void> aufgabeLoeschen(@PathVariable UUID aufgabeId,
                                                @AuthenticationPrincipal Benutzer benutzer) {
        aufgabeService.aufgabeLoeschen(aufgabeId, benutzer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{aufgabeId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER', 'MITARBEITER')")
    public ResponseEntity<AufgabeResponse> statusAendern(@PathVariable UUID aufgabeId,
                                                         @RequestBody AufgabeStatusRequest request,
                                                         @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(aufgabeService.statusAendern(aufgabeId, request, benutzer));
    }

    @PutMapping("/{aufgabeId}/zuweisung")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER', 'MITARBEITER')")
    public ResponseEntity<AufgabeResponse> aufgabeZuweisen(@PathVariable UUID aufgabeId,
                                                           @RequestBody AufgabeZuweisenRequest request,
                                                           @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(aufgabeService.aufgabeZuweisen(aufgabeId, request.getBenutzerId(), benutzer));
    }
}
