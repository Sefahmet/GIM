package com.gim.project.Controller;

import com.gim.project.Entity.Activity;
import com.gim.project.Service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activites")
public class ActivityController {
    private final ActivityService activityService;
    @GetMapping(value = "/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity("Welcome to activity services.",HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Activity>> getAllCustomers(){
        try{
            return new ResponseEntity(activityService.getAllActivities(), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
