package com.example.demo.repository;

import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BenutzerRepository extends JpaRepository<Benutzer, UUID> {
    Optional<Benutzer> findByEmail(String email);

    List<Benutzer> findByMandant(Mandant mandant);
}
