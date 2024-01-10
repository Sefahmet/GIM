package com.gim.project.Controller;

import com.gim.project.Entity.Activity;
import com.gim.project.Entity.CropSpecies;
import com.gim.project.Service.CropSpeciesService;
import lombok.Getter;
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
@RequestMapping("/api/cropspecies")
public class CropSpeciesController {
    private final CropSpeciesService cropSpeciesService;

    @GetMapping("/all")
    public ResponseEntity<List<CropSpecies>> getAllCustomers(){
        try{
            return new ResponseEntity(cropSpeciesService.getAllCropSpecies(), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
