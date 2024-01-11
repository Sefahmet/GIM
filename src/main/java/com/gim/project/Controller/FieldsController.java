package com.gim.project.Controller;

import com.gim.project.Entity.Fields;
import com.gim.project.Entity.WFSLayer;
import com.gim.project.Service.FieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
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
@RequestMapping("/api/fields")
public class FieldsController {
    private final FieldService fieldService;

    @GetMapping("/all")
    public ResponseEntity<List<Fields>> getAllFields(){
        try{
            return new ResponseEntity(fieldService.getAllFields(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/limit")
    public ResponseEntity<List<Fields>> getLimitedFields(@RequestParam Integer limit){
        try{
            return new ResponseEntity(fieldService.getLimitedFields(limit),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/bbox")
    public ResponseEntity<List<Fields>> getFieldsByBbox(@RequestParam Double x1,
                                                        @RequestParam Double y1,
                                                        @RequestParam Double x2,
                                                        @RequestParam Double y2 ){
        try{
            List<Fields> fields = fieldService.getFieldsByBbox(x1, y1, x2, y2);
            if(fields.isEmpty()){
                return new ResponseEntity("com.gim.project.field.empty",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(fields,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/point")
    public ResponseEntity<List<Fields>> getFieldsByPoint(@RequestParam Double lat,
                                                        @RequestParam Double lon){
        try{
            Point point = WFSLayer.transformation(lat, lon, "4326", "3044");
            Double y = point.getX();
            Double x = point.getY();
            Fields field = fieldService.getFieldsByPoint(x,y);
            if(field == null){
                return new ResponseEntity("com.gim.project.field.empty",HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(field,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
}
