package com.gim.project.Controller;

import com.gim.project.Entity.Activity;
import com.gim.project.Entity.CityDistrict;
import com.gim.project.Service.CityDistrictService;
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
@RequestMapping("/api/citydistrict")
public class CityDistrictController {
    private final CityDistrictService cityDistrictService;
    @GetMapping(value = "/all")
    public ResponseEntity<List<CityDistrict>> getAllCustomers(){

        try{
            return new ResponseEntity(cityDistrictService.getAllCityDistricts(), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
