package com.gim.project.Controller;

import com.gim.project.Entity.FieldWFS;
import com.gim.project.Entity.WFSLayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import viewer.symbols.SymbolFactory;

import java.util.List;
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fieldswfs")
public class FieldsWFSController {

    @GetMapping("/all")
    public ResponseEntity<List<FieldWFS>> getAllFields(){
        String sUrl = "http://131.220.71.188:8080/geoserver/geo/ows";
        WFSLayer wfsLayer = new WFSLayer(sUrl, "fields");

        try {
            List<FieldWFS> res = wfsLayer.WFSquery();
            if (res.isEmpty()){
                return new ResponseEntity("Fields Not Found",HttpStatus.NOT_FOUND);

            }else{
                return new ResponseEntity(res,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
}
