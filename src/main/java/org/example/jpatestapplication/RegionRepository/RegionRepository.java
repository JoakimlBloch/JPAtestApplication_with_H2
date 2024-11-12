package org.example.jpatestapplication.RegionRepository;

import org.example.jpatestapplication.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
}
