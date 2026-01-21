package com.city.district;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
  Optional<District> findByName(String name);
  Optional<District> findBySlug(String slug);
}