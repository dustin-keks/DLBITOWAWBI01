package com.example.demo.dto;

import com.example.demo.entity.enums.AufgabeStatus;
import lombok.Getter;

@Getter
public class AufgabeStatusRequest {
    private AufgabeStatus status;
}
