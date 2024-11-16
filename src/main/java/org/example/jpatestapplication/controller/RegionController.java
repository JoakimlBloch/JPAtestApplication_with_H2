package org.example.jpatestapplication.controller;

import org.example.jpatestapplication.model.Region;
import org.example.jpatestapplication.repository.RegionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegionController {

    private RegionRepository regionRepository;

    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping("/regioner")
    public List<Region> getAll() {
        return regionRepository.findAll();
    }
}
