package com.gim.project.Service.impl;

import com.gim.project.Entity.Street;
import com.gim.project.Repository.StreetRepository;
import com.gim.project.Service.StreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreetsServiceImpl implements StreetService {
    private final StreetRepository streetRepository;

    @Override
    public List<Street> getAllStreets() {
        try{
            return streetRepository.findAllGeometryAsText();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Street> getStreetsWithLimit(Integer limitValue) {
        try{
            return streetRepository.getStreetsWithLimitGeometryAsText(limitValue);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Street> getStreetsByBbox(Double x1,Double y1, Double x2,Double y2) {
        try{

            Integer srid = getStreetsWithLimit(1).get(0).getSrid();
            return streetRepository.getStreetByBbox(x1,y1,x2,y2,srid);
        }catch (Exception e){
            throw e;
        }
    }

}
