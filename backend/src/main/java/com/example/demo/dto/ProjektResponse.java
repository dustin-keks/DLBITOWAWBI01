package com.example.demo.dto;

import com.example.demo.entity.enums.ProjektStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProjektResponse {
    private UUID id;
    private String name;
    private ProjektStatus status;
    private double fortschritt;
}
