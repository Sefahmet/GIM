package com.gim.project.Service.impl;

import com.gim.project.Entity.Fields;
import com.gim.project.Repository.FieldsRepository;
import com.gim.project.Service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private  final FieldsRepository fieldsRepository;
    @Override
    public List<Fields> getAllFields() {
        try{
            return fieldsRepository.getAllFieldsByGeometryAsText();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Fields> getLimitedFields(Integer limit) {
        try{
            return fieldsRepository.getLimitedFieldsGeometryAsText(limit);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Fields> getFieldsByBbox(Double x1, Double y1, Double x2, Double y2) {
        try{
            Integer srid = fieldsRepository.getLimitedFieldsGeometryAsText(1).get(0).getSrid();
            return fieldsRepository.getFieldsByBBOX(x1,y1,x2,y2,srid);
        }catch (Exception e){
            throw e;
        }
    }
}
