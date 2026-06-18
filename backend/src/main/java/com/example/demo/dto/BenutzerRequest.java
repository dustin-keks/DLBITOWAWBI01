package com.example.demo.dto;

import com.example.demo.entity.enums.BenutzerRolle;
import lombok.Getter;

@Getter
public class BenutzerRequest {
    private String name;
    private String email;
    private String passwort;
    private BenutzerRolle rolle;
}
