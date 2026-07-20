package com.labelflow.labelchange.master.country.repository;

import com.labelflow.labelchange.master.country.entity.Country;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Short> {

    Optional<Country> findByCountryCode(String countryCode);

    Optional<Country> findByIsoCode(String isoCode);

    Optional<Country> findByCountryNameIgnoreCase(String countryName);

    List<Country> findByActiveTrueOrderByCountryNameAsc();

    boolean existsByCountryCode(String countryCode);

    boolean existsByIsoCode(String isoCode);

}