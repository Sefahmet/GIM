package com.gim.project.Service.impl;

import com.gim.project.Entity.Treatment;
import com.gim.project.Repository.TreatmentRepository;
import com.gim.project.Service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;
    @Override
    public List<Treatment> getTreatmentsByFid(String fid) {
        try {
            return treatmentRepository.getTreatmentByFid(fid);
        }catch (Exception e){
            throw e;
        }
    }
}
