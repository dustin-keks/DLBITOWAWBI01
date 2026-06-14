package com.example.demo.entity;

import com.example.demo.entity.enums.ProjektStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Projekt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProjektStatus status;

    @ManyToOne
    @JoinColumn(name = "mandant_id")
    private Mandant mandant;

    @ManyToMany(mappedBy = "projekte")
    private Set<Benutzer> mitarbeitende = new HashSet<>();

    @OneToMany(mappedBy = "projekt", cascade = CascadeType.ALL)
    private Set<Aufgabe> aufgaben = new HashSet<>();
}
