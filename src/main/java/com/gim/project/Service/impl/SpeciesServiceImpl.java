package com.gim.project.Service.impl;

import com.gim.project.Entity.Species;
import com.gim.project.Repository.SpeciesRepository;
import com.gim.project.Service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesServiceImpl implements SpeciesService {
    private final SpeciesRepository speciesRepository;


    @Override
    public List<Species> getByFid(String fid) {
        try {
        return speciesRepository.getSpeciesByFid(fid);
        }catch (Exception e){
            throw e;

        }
    }

    @Override
    public HashMap<String,List<String>> getAllPlants() {
        List<Species> species =  speciesRepository.findAll();
        HashMap<String,List<String>> uniquePlant = new HashMap<>();
        for(Species species1:species){
            if(!uniquePlant.keySet().contains(species1.getPlant())){
                List<String> lst = new ArrayList<>();
                lst.add(species1.getFid());
                uniquePlant.put(species1.getPlant(),lst);
            }else {
                uniquePlant.get(species1.getPlant()).add(species1.getFid());
            }
        }
        return uniquePlant;
    }
}
