package com.example.demo.controller;

import com.example.demo.dto.AufgabeRequest;
import com.example.demo.dto.AufgabeResponse;
import com.example.demo.dto.AufgabeStatusRequest;
import com.example.demo.entity.Benutzer;
import com.example.demo.service.AufgabeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AufgabeResponse> aufgabeAnlegen(@PathVariable UUID projektId,
                                                          @RequestBody AufgabeRequest request,
                                                          @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(aufgabeService.aufgabeAnlegen(projektId, request, benutzer));
    }

    @PutMapping("/{aufgabeId}/status")
    public ResponseEntity<AufgabeResponse> statusAendern(@PathVariable UUID aufgabeId,
                                                         @RequestBody AufgabeStatusRequest request,
                                                         @AuthenticationPrincipal Benutzer benutzer) {
        return ResponseEntity.ok(aufgabeService.statusAendern(aufgabeId, request, benutzer));
    }
}
