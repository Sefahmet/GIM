package com.gim.project.Controller;

import com.gim.project.Entity.SpatialReferenceSystem;
import com.gim.project.Service.SRIDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/srid")
public class SRIDController {
    private final SRIDService sridService;

    @GetMapping("/getproj4byid")
    public ResponseEntity<String> getById(@RequestParam Integer srid){
        try{
            SpatialReferenceSystem sridObj = sridService.getSRID(srid);
            ResponseEntity responseEntity= new ResponseEntity(sridObj.getProj4(), HttpStatus.OK);
            System.out.println(responseEntity);
            return responseEntity;
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

}




