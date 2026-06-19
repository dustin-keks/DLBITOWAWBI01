package com.example.demo.controller;

import com.example.demo.dto.ProjektRequest;
import com.example.demo.dto.ProjektResponse;
import com.example.demo.entity.Benutzer;
import com.example.demo.service.ProjektService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projekte")
@RequiredArgsConstructor
public class ProjektController {
    private final ProjektService projektService;

    @GetMapping
    public ResponseEntity<List<ProjektResponse>> getMeineProjekte(@AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(projektService.getMeineProjekte(benutzer));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER')")
    public ResponseEntity<ProjektResponse> projektAnlegen(@RequestBody ProjektRequest request,
                                                          @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(projektService.projektAnlegen(request, benutzer));
    }

    @PutMapping("/{id}/archivieren")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER')")
    public ResponseEntity<ProjektResponse> projektArchivieren(@PathVariable UUID id) {
        return ResponseEntity.ok(projektService.projektArchivieren(id));
    }

    @PostMapping("/{projektId}/mitarbeiter/{benutzerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER')")
    public ResponseEntity<ProjektResponse> mitarbeiterZuordnen(@PathVariable UUID projektId,
                                                               @PathVariable UUID benutzerId) {
        return ResponseEntity.ok(projektService.mitarbeiterZuordnen(projektId, benutzerId));
    }
}
