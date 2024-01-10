package com.gim.project.Service.impl;

import com.gim.project.Entity.Activity;
import com.gim.project.Repository.ActivityRepository;
import com.gim.project.Service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {
     private final ActivityRepository activityRepository;


    @Override
    public List<Activity> getAllActivities() {
        try {
            List<Activity> activities = activityRepository.findAll();
            return activities;
        }catch (Exception e){
            throw e;
        }

    }
}
