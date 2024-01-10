package com.gim.project.Service;

import com.gim.project.Entity.Fields;

import java.util.List;

public interface FieldService {
    List<Fields> getAllFields();
    List<Fields> getLimitedFields(Integer limit);
    List<Fields> getFieldsByBbox(Double x1,Double y1, Double x2,Double y2);
}
