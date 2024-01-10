package com.gim.project.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@AllArgsConstructor
public class FieldWFS {
    @Getter @Setter private Integer id;
    @Getter @Setter private String fid;
    @Getter @Setter private String soil;
    @Getter @Setter private Integer srid;
    @Getter @Setter private List<Coordinate> geometry;

}
