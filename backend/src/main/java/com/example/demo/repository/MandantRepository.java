package com.example.demo.repository;

import com.example.demo.entity.Mandant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MandantRepository extends JpaRepository<Mandant, UUID> {
}
