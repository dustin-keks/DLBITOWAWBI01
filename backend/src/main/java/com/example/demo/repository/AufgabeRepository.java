package com.example.demo.repository;

import com.example.demo.entity.Aufgabe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AufgabeRepository extends JpaRepository<Aufgabe, UUID> {
    List<Aufgabe> findByProjektId(UUID projectId);
}
