package com.gim.project.Repository;

import com.gim.project.Entity.CityDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDistrictRepository extends JpaRepository<CityDistrict, Integer> {
    @Query(value = "SELECT id, name, flaeche, ST_AsText(geom) AS geometry,st_srid(geom) srid FROM ex2.citydistrict", nativeQuery = true)
    List<CityDistrict> findAllWithGeometryText();
}
