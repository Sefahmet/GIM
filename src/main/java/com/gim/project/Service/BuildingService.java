package com.gim.project.Service;

import com.gim.project.Entity.Building;

import java.util.List;

public interface BuildingService {
    List<Building> getAllBuildings();
    List<Building> getAllBuildingsWithLimit(Integer limit);
    List<Building> getBuildingsWithBbox(Double x1,Double y1,Double x2, Double y2);
}
