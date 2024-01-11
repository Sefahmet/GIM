package com.gim.project.Repository;

import com.gim.project.Entity.Species;
import com.gim.project.Entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment,Integer> {
    @Query(value = "SELECT * FROM public.treatments where field_id = :fid", nativeQuery = true)
    List<Treatment> getTreatmentByFid(@Param("fid") String fid);

}
