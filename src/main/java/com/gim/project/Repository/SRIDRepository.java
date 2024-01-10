package com.gim.project.Repository;

import com.gim.project.Entity.SpatialReferenceSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public interface SRIDRepository extends JpaRepository<SpatialReferenceSystem,Integer> {

    @Query(value = "select * from spatial_ref_sys where srid = :sridValue", nativeQuery = true)

    List<SpatialReferenceSystem> getByID(@Param("sridValue") Integer srid);
}
