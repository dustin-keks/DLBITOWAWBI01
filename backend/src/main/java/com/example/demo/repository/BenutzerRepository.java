package com.example.demo.repository;

import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Mandant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BenutzerRepository extends JpaRepository<Benutzer, UUID> {
    Optional<Benutzer> findByEmail(String email);

    List<Benutzer> findByMandant(Mandant mandant);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM projekt_mitglieder", nativeQuery = true)
    void projektZuordnungLoeschen();
}
