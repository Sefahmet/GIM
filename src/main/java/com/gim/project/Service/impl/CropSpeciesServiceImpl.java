package com.gim.project.Service.impl;

import com.gim.project.Entity.CropSpecies;
import com.gim.project.Repository.CropSpeciesRepository;
import com.gim.project.Service.CropSpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropSpeciesServiceImpl implements CropSpeciesService {
    private final CropSpeciesRepository cropSpeciesRepository;
    @Override
    public List<CropSpecies> getAllCropSpecies() {
        try {
            List<CropSpecies> cropSpecies = cropSpeciesRepository.findAllWithGeometryText();
            return cropSpecies;
        }catch (Exception e){
            throw e;
        }
    }
}
