package com.gim.project.Service;

import com.gim.project.Entity.SpatialReferenceSystem;

public interface SRIDService {
    SpatialReferenceSystem getSRID(Integer srid) throws Exception;
}
