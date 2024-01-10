package com.gim.project.Repository;

import com.gim.project.Entity.Gardener;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
@Repository
public interface GardenerRepository extends JpaRepository<Gardener, Integer> {
}
