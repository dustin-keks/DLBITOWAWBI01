package com.example.demo.dto;

import com.example.demo.entity.enums.ProjektStatus;
import lombok.Getter;

@Getter
public class ProjektStatusRequest {
    private ProjektStatus status;
}
