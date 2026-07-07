package com.example.demo.entity;

import com.example.demo.entity.enums.AufgabeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Aufgabe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titel;

    private String beschreibung;

    @Enumerated(EnumType.STRING)
    private AufgabeStatus status;

    @ManyToOne
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;
}
