package com.gim.project.Service;

import com.gim.project.Entity.Street;

import java.util.List;

public interface StreetService {
    List<Street> getAllStreets();
    List<Street> getStreetsWithLimit(Integer limitValue);
    List<Street> getStreetsByBbox(Double x1,Double y1, Double x2,Double y2);
}
