package com.example.demo.dto;

import com.example.demo.entity.enums.BenutzerRolle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BenutzerResponse {
    private UUID uuid;
    private String name;
    private String email;
    private BenutzerRolle rolle;
}
