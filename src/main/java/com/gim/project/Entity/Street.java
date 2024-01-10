package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "streets",schema = "ex2")
public class Street {
    @Id
    @Column(name = "id")
    @Getter @Setter private Integer id;

    @Column(name = "osm_id")
    @Getter @Setter private Long osmid;

    @Column(name = "name")
    @Getter @Setter private String name;

    @Column(name = "type")
    @Getter @Setter private String type;

    @Column(name = "maxspeed")
    @Getter @Setter private Integer maxSpeed;

    @Column(name = "shape_leng")
    @Getter @Setter private Double length;

    @Column(name = "srid")
    @Getter @Setter private Integer srid;

    @Column(name = "geometry")
    @Getter @Setter private String geometry;


}
