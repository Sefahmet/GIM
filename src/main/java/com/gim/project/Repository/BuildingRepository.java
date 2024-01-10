package com.gim.project.Repository;

import com.gim.project.Entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
    @Query(value = "SELECT id,osm_id,name,type, ST_AsText(geom) as  geometry,st_srid(geom) as srid FROM ex2.buildings", nativeQuery = true)
    List<Building> getAllBuildingsGeometryAsText();

    @Query(value = "SELECT id,osm_id,name,type, ST_AsText(geom) as geometry,st_srid(geom) as srid FROM ex2.buildings LIMIT :limiter", nativeQuery = true)
    List<Building> getAllBuildingsWithLimitGeometryAsText(@Param("limiter") Integer limiter);




    @Query(value = "SELECT id,osm_id,name,type, ST_AsText(geom) as  geometry,st_srid(geom) as srid " +
            "FROM ex2.buildings " + // Entity adını doğru bir şekilde belirtin
            "WHERE ST_Intersects(geom, ST_SetSRID(ST_MakeBox2D(ST_Point(:x1, :y1), ST_Point(:x2, :y2)), :srid1))", nativeQuery = true)
    List<Building> getBuildingsByBBOX(@Param("x1") Double x1,
                                      @Param("y1") Double y1,
                                      @Param("x2") Double x2,
                                      @Param("y2") Double y2,
                                      @Param("srid1") Integer srid1);

}
