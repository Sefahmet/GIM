package com.gim.project.Repository;

import com.gim.project.Entity.Building;
import com.gim.project.Entity.Fields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FieldsRepository extends JpaRepository<Fields,Integer> {
    @Query(value = "SELECT fid,field_id,soil, ST_AsText(geom) as  geometry,st_srid(geom) as srid FROM public.fields", nativeQuery = true)
    List<Fields> getAllFieldsByGeometryAsText();

    @Query(value = "SELECT fid,field_id,soil, ST_AsText(geom) as  geometry,st_srid(geom) as srid FROM public.fields LIMIT :limiter", nativeQuery = true)
    List<Fields> getLimitedFieldsGeometryAsText(@Param("limiter") Integer limiter);




    @Query(value = "SELECT fid,field_id,soil, ST_AsText(geom) as  geometry,st_srid(geom) as srid " +
            "FROM public.fields " + // Entity adını doğru bir şekilde belirtin
            "WHERE ST_Intersects(geom, ST_SetSRID(ST_MakeBox2D(ST_Point(:x1, :y1), ST_Point(:x2, :y2)), :srid1))", nativeQuery = true)
    List<Fields> getFieldsByBBOX(@Param("x1") Double x1,
                                      @Param("y1") Double y1,
                                      @Param("x2") Double x2,
                                      @Param("y2") Double y2,
                                      @Param("srid1") Integer srid1);
    @Query(value = "SELECT fid,field_id,soil, ST_AsText(geom) as  geometry,st_srid(geom) as srid " +
            "FROM public.fields " +
            "WHERE ST_Intersects(geom, ST_SetSRID(ST_Point(:x, :y), :srid1))", nativeQuery = true)
    List<Fields> getFieldsByPoint(@Param("x") Double x1,
                                 @Param("y") Double y1,
                                 @Param("srid1") Integer srid1);
}
