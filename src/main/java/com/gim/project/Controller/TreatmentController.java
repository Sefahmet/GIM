package com.gim.project.Controller;

import com.gim.project.Entity.Treatment;
import com.gim.project.Service.TreatmentService;
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
@RequestMapping("/api/treatment")
public class TreatmentController {
    private final TreatmentService treatmentService;
    @GetMapping("/fid")
    public ResponseEntity<List<Treatment>> getByFid(@RequestParam String fid){
        try{
            return new ResponseEntity(treatmentService.getTreatmentsByFid(fid), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
}
