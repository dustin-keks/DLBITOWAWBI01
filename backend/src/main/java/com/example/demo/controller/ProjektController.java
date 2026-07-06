package com.example.demo.controller;

import com.example.demo.dto.ProjektRequest;
import com.example.demo.dto.ProjektResponse;
import com.example.demo.dto.ProjektStatusRequest;
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

    @GetMapping("/{projektId}")
    public ResponseEntity<ProjektResponse> getProjekt(@PathVariable UUID projektId,
                                                      @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(projektService.getProjekt(projektId, benutzer));
    }

    @PutMapping("/{projektId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER')")
    public ResponseEntity<ProjektResponse> projektAktualisieren(@PathVariable UUID projektId,
                                                                @RequestBody ProjektRequest request) {
        return ResponseEntity.ok(projektService.projektAktualisieren(projektId, request));
    }

    @PatchMapping("/{projektId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER')")
    public ResponseEntity<ProjektResponse> projektArchivieren(@PathVariable UUID projektId,
                                                              @RequestBody ProjektStatusRequest req) {
        return ResponseEntity.ok(projektService.statusAendern(projektId, req.getStatus()));
    }

    @PostMapping("/{projektId}/mitarbeiter/{benutzerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJEKTLEITER')")
    public ResponseEntity<ProjektResponse> mitarbeiterZuordnen(@PathVariable UUID projektId,
                                                               @PathVariable UUID benutzerId) {
        return ResponseEntity.ok(projektService.mitarbeiterZuordnen(projektId, benutzerId));
    }
}
