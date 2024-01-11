package com.gim.project.Service;

import com.gim.project.Entity.Species;

import java.util.List;

public interface SpeciesService {
    public List<Species> getByFid(String fid);
}
