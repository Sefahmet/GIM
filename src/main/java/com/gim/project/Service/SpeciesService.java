package com.gim.project.Service;

import com.gim.project.Entity.Species;

import java.util.HashMap;
import java.util.List;

public interface SpeciesService {
    List<Species> getByFid(String fid);
    HashMap<String,List<String>> getAllPlants();
}
