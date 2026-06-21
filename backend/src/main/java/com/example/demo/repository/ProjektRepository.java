package com.example.demo.repository;

import com.example.demo.entity.Benutzer;
import com.example.demo.entity.Projekt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjektRepository extends JpaRepository<Projekt, UUID> {
    List<Projekt> findByMitarbeitendeContaining(Benutzer benutzer);
}
