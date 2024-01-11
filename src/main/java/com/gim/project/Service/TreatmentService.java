package com.gim.project.Service;

import com.gim.project.Entity.Treatment;

import java.util.List;

public interface TreatmentService {
    public List<Treatment> getTreatmentsByFid(String fid);
}
