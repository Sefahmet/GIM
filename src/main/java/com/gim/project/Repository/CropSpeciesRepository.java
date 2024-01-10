package com.gim.project.Repository;

import com.gim.project.Entity.CropSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropSpeciesRepository extends JpaRepository<CropSpecies,Integer> {
    @Query(value = "SELECT id, name, frostres, minsd, maxsd, section, ST_AsText(geom) AS geometry,st_srid(geom) srid FROM ex2.cropspecies", nativeQuery = true)
    List<CropSpecies> findAllWithGeometryText();

}
