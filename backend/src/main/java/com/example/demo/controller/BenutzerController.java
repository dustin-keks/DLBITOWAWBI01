package com.example.demo.controller;

import com.example.demo.dto.BenutzerRequest;
import com.example.demo.dto.BenutzerResponse;
import com.example.demo.entity.Benutzer;
import com.example.demo.service.BenutzerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/benutzer")
@RequiredArgsConstructor
public class BenutzerController {
    private final BenutzerService benutzerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BenutzerResponse>> getAlleBenutzer(@AuthenticationPrincipal Benutzer admin) {
        return ResponseEntity.ok(benutzerService.getAlleBenutzer(admin));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BenutzerResponse> benutzerAnlegen(@RequestBody BenutzerRequest request,
                                                            @AuthenticationPrincipal Benutzer admin) {
        return ResponseEntity.ok(benutzerService.benutzerAnlegen(request, admin));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BenutzerResponse> benutzerAktualisieren(@PathVariable UUID id,
                                                            @RequestBody BenutzerRequest request) {
        return ResponseEntity.ok(benutzerService.benutzerAktualisieren(id, request));
    }
}
