package org.example.jpatestapplication.controller;

import org.example.jpatestapplication.repository.KommuneRepository;
import org.example.jpatestapplication.model.Kommune;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class KommuneController {

    private KommuneRepository kommuneRepository;

    public KommuneController(KommuneRepository kommuneRepository) {
        this.kommuneRepository = kommuneRepository;
    }

    @GetMapping("/kommuner")
    public List<Kommune> getByRegion(@RequestParam(required = false) String regionskode) {
        return regionskode == null
                ? kommuneRepository.findAll()
                : kommuneRepository.findByRegion_Kode(regionskode);
    }

    @PostMapping("/kommuner/remove")
    public void removeKommune(@RequestParam int id) {
        Optional<Kommune> kommune = kommuneRepository.findById(id);
        if (kommune.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kommune ikke fundet");
        }
        kommuneRepository.delete(kommune.get());
    }

}
