package com.gim.project.Entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spatial_ref_sys",schema = "public")
public class SpatialReferenceSystem {
    @Id
    @Column(name = "srid")
    private Integer srid;

    @Column(name = "srtext")
    @Getter private String srtext;

    @Column(name = "proj4text")
    @Getter private String proj4;

    @Override
    public String toString(){
        return "SRID : " + srid+
                "\nSrtText : " + srtext
                +"\nProj4Text : " + proj4;

    }
}
