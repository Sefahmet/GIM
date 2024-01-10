package com.gim.project.Service.impl;

import com.gim.project.Entity.Building;
import com.gim.project.Repository.BuildingRepository;
import com.gim.project.Service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    private final BuildingRepository buildingRepository;
    @Override
    public List<Building> getAllBuildings() {
        try{
            return buildingRepository.getAllBuildingsGeometryAsText();

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Building> getAllBuildingsWithLimit(Integer limit) {
        try{
            return buildingRepository.getAllBuildingsWithLimitGeometryAsText(limit);

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Building> getBuildingsWithBbox(Double x1, Double y1, Double x2, Double y2) {
        try{
            Integer srid = buildingRepository.getAllBuildingsWithLimitGeometryAsText(1).get(0).getSrid();

            return buildingRepository.getBuildingsByBBOX(x1,y1,x2,y2,srid);

        }catch (Exception e){
            throw e;
        }
    }


}
