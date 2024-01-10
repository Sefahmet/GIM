package com.gim.project.Controller;

import com.gim.project.Entity.Building;
import com.gim.project.Service.BuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buildings")
public class BuildingController {
    private final BuildingService buildingService;
    @GetMapping("/all")
    public ResponseEntity<List<Building>> getAllBuildings(){
        try{
            return new ResponseEntity(buildingService.getAllBuildings(), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/limit")
    public ResponseEntity<List<Building>> getAllBuildingsAsLimited(@RequestParam Integer limit){
        try{
            return new ResponseEntity(buildingService.getAllBuildingsWithLimit(limit), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bbox")
    public ResponseEntity<List<Building>> getAllBuildingsWBbox(@RequestParam Double x1,
                                                               @RequestParam Double y1,
                                                               @RequestParam Double x2,
                                                               @RequestParam Double y2){
        try{
            List<Building> buildings = buildingService.getBuildingsWithBbox(x1, y1, x2, y2);
            if(buildings.isEmpty()){
                return new ResponseEntity("There is no Building in this Area", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(buildings, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
