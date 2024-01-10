package com.gim.project.Repository;

import com.gim.project.Entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    @Query(value = "SELECT id, name, planted, ST_AsText(geom) AS geometry,st_srid(geom) srid FROM ex2.section", nativeQuery = true)
    List<Section> findAllGeometryAsText();

}
