package com.gim.project.Service.impl;

import com.gim.project.Entity.CityDistrict;
import com.gim.project.Repository.CityDistrictRepository;
import com.gim.project.Service.CityDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityDistricServiceImpl implements CityDistrictService {
    private final CityDistrictRepository cityDistrictRepository;
    @Override
    public List<CityDistrict> getAllCityDistricts() {
        try{
            return cityDistrictRepository.findAllWithGeometryText();

        }catch (Exception e){
            throw e;
        }
    }
}
