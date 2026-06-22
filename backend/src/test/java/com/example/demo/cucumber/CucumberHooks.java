package com.example.demo.cucumber;

import com.example.demo.repository.AufgabeRepository;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.MandantRepository;
import com.example.demo.repository.ProjektRepository;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class CucumberHooks {
    @Autowired
    private AufgabeRepository aufgabeRepository;

    @Autowired
    private ProjektRepository projektRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private MandantRepository mandantRepository;

    @Before
    public void before() {
        aufgabeRepository.deleteAll();
        projektRepository.deleteAll();
        benutzerRepository.deleteAll();
        mandantRepository.deleteAll();
    }
}
