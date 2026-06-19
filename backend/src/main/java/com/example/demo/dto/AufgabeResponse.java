package com.example.demo.dto;

import com.example.demo.entity.enums.AufgabeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AufgabeResponse {
    private UUID id;
    private String titel;
    private AufgabeStatus status;
}
