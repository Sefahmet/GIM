package com.gim.project.Service.impl;

import com.gim.project.Entity.Species;
import com.gim.project.Repository.SpeciesRepository;
import com.gim.project.Service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
