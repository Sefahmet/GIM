package com.gim.project.Controller;

import com.gim.project.Entity.FieldWFS;
import com.gim.project.Entity.SparQLQuery;
import com.gim.project.Entity.WFSLayer;
import com.gim.project.Service.SparQLService;
import com.gim.project.config.GIMConfig;
import com.gim.project.operations.GIMQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fuseki")
public class FusekiController {


        private final SparQLService sparQLService;

        @GetMapping("/de2en")
        public ResponseEntity<String> translateDe2En(@RequestParam String deLabel){
            try{
                String enLabel = sparQLService.translateDe2En(deLabel);
                if(enLabel!=null) {
                    return new ResponseEntity(enLabel, HttpStatus.OK);
                }else {
                    return new ResponseEntity("", HttpStatus.NOT_FOUND);
                }
        }catch (Exception e){
                return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
            }
        }

    @GetMapping("/image")
    public ResponseEntity<String> getImage(@RequestParam String deLabel){
        try{
            System.out.println("label : " + deLabel);
            String imageURL = sparQLService.getImage(deLabel);
            if(imageURL!=null) {
                return new ResponseEntity(imageURL, HttpStatus.OK);
            }else {
                return new ResponseEntity("", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/description")
    public ResponseEntity<String> getDescription(@RequestParam String deLabel){
        try{
            String description = sparQLService.getDescription(deLabel);
            System.out.println(description);
            if(description!=null) {
                return new ResponseEntity(description, HttpStatus.OK);
            }else {
                return new ResponseEntity("", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
    @GetMapping("/dictionary")
    public ResponseEntity<String> getDictionary(){
        try{
            HashMap<String,String> dictionary = sparQLService.getAllEnglishVersions();

            if(dictionary!=null) {

                return new ResponseEntity(dictionary, HttpStatus.OK);
            }else {
                return new ResponseEntity("", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

}
