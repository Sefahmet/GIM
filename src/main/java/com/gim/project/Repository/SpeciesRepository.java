package com.gim.project.Repository;

import com.gim.project.Entity.Fields;
import com.gim.project.Entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SpeciesRepository extends JpaRepository<Species,Integer> {
    @Query(value = "SELECT * FROM public.species where field_id = :fid", nativeQuery = true)
    List<Species> getSpeciesByFid(@Param("fid") String fid);



}
