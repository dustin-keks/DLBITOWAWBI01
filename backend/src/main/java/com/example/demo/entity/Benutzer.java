package com.example.demo.entity;

import com.example.demo.entity.enums.BenutzerRolle;
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
public class Benutzer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String passwort;

    @Enumerated(EnumType.STRING)
    private BenutzerRolle rolle;

    @ManyToOne
    @JoinColumn(name = "mandant_id")
    private Mandant mandant;

    @ManyToMany
    @JoinTable(
            name = "projekt_mitglieder",
            joinColumns = @JoinColumn(name = "benutzer_id"),
            inverseJoinColumns = @JoinColumn(name = "projekt_id")
    )
    private Set<Projekt> projekte = new HashSet<>();
}
