package org.example.jpatestapplication.repository;

import org.example.jpatestapplication.model.Kommune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KommuneRepository extends JpaRepository<Kommune, Integer> {
    List<Kommune> findByRegion_Kode(String regionskode);
}
