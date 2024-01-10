package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cropspecies",schema = "ex2")
public class CropSpecies {
    @Id
    @Column(name = "id")
    @Getter @Setter private Integer id;

    @Column(name = "name")
    @Getter @Setter private String name;

    @Column(name = "frostres")
    @Getter @Setter private boolean frostres;

    @Column(name = "minsd")
    @Getter @Setter private Integer minsd;

    @Column(name = "maxsd")
    @Getter @Setter private Integer maxsd;

    @Column(name = "section")
    @Getter @Setter private Integer section;

    @Column(name = "srid")
    @Getter @Setter private Integer srid;

    @Column(name = "geometry")
    @Getter @Setter private String geometry;

}
