package com.example.demo.dto;

import com.example.demo.entity.enums.BenutzerRolle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String name;
    private BenutzerRolle rolle;
}
