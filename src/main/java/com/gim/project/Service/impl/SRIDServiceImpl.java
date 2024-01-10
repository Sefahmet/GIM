package com.gim.project.Service.impl;

import com.gim.project.Entity.SpatialReferenceSystem;
import com.gim.project.Repository.SRIDRepository;
import com.gim.project.Service.SRIDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SRIDServiceImpl implements SRIDService {
    private final SRIDRepository sridRepository;
    @Override
    public SpatialReferenceSystem getSRID(Integer srid) throws Exception {
        List<SpatialReferenceSystem> obj = sridRepository.getByID(srid);
        obj.stream().forEach(System.out::println);
        if(obj.isEmpty()){
            throw new Exception("SRID not Found");
        }
        System.out.println(obj.get(0));
        return obj.get(0);
    }
}
