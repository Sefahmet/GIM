package com.gim.project.Controller;

import com.gim.project.Entity.Activity;
import com.gim.project.Entity.Building;
import com.gim.project.Entity.Street;
import com.gim.project.Service.StreetService;
import com.sun.istack.NotNull;
import lombok.Getter;
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
@RequestMapping("/api/streets")
public class StreetController {
    private final StreetService streetService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Street>> getAllCustomers(){
        try{
            return new ResponseEntity(streetService.getAllStreets(), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/limit")
    public ResponseEntity<List<Street>> getStreetAsLimted(@NotNull @RequestParam Integer limiter){
        try{
            return new ResponseEntity(streetService.getStreetsWithLimit(limiter), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/bbox")
    public ResponseEntity<List<Street>> getAllBuildingsWBbox(@RequestParam Double x1,
                                                               @RequestParam Double y1,
                                                               @RequestParam Double x2,
                                                               @RequestParam Double y2){
        try{
            List<Street> streets = streetService.getStreetsByBbox(x1, y1, x2, y2);
            if(streets.isEmpty()){
                return new ResponseEntity("There is no Street in this Area", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(streets, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
