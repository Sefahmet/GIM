package com.gim.project.Controller;

import com.gim.project.Entity.Species;
import com.gim.project.Service.SparQLService;
import com.gim.project.Service.SpeciesService;
import com.gim.project.Service.impl.SparQLServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/species")
public class SpeciesController {
    private final SpeciesService speciesService;
    private final SparQLService sparQLService;
    @GetMapping("/fid")
    public ResponseEntity<List<Species>> getSpeciesByFid(@RequestParam String fid){
        try{

            return new ResponseEntity(speciesService.getByFid(fid), HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/all-plants")
    public ResponseEntity<HashMap<String,List<String>>> getAllPlants(){
        try{

            return new ResponseEntity(speciesService.getAllPlants(), HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
}
