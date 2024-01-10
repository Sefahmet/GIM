package com.gim.project.Repository;

import com.gim.project.Entity.Building;
import com.gim.project.Entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street,Integer> {
    @Query(value = "SELECT id,osm_id, name, type,maxspeed,shape_leng, ST_AsText(geom) AS geometry,st_srid(geom) srid FROM ex2.streets", nativeQuery = true)
    List<Street> findAllGeometryAsText();

    @Query(value = "SELECT id, osm_id, name, type, maxspeed, shape_leng, ST_AsText(geom) AS geometry,st_srid(geom) srid FROM ex2.streets LIMIT :limitValue", nativeQuery = true)
    List<Street> getStreetsWithLimitGeometryAsText(@Param("limitValue") Integer limitValue);

    @Query(value = "SELECT id, osm_id, name, type, maxspeed, shape_leng, ST_AsText(geom) AS geometry,st_srid(geom) srid " +
            "FROM ex2.streets " +
            "WHERE ST_Intersects(geom, ST_SetSRID(ST_MakeBox2D(ST_Point(:x1, :y1), ST_Point(:x2, :y2)), :srid1))", nativeQuery = true)
    List<Street> getStreetByBbox(@Param("x1") Double x1,
                                      @Param("y1") Double y1,
                                      @Param("x2") Double x2,
                                      @Param("y2") Double y2,
                                      @Param("srid1") Integer srid1);

}
