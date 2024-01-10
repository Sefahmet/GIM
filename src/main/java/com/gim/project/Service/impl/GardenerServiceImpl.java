package com.gim.project.Service.impl;

import com.gim.project.Entity.Activity;
import com.gim.project.Entity.Gardener;
import com.gim.project.Repository.GardenerRepository;
import com.gim.project.Service.GardenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GardenerServiceImpl implements GardenerService {
    private final GardenerRepository gardenerRepository;


    @Override
    public List<Gardener> getAllGardeners() {
        try {
            List<Gardener> gardeners = gardenerRepository.findAll();
            return gardeners;
        }catch (Exception e){
            throw e;
        }
    }
}
