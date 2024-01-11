package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "species",schema = "public")
public class Species {
    @Id
    @Column(name = "id")
    @Getter @Setter private Integer id;

    @Column(name = "field_id")
    @Getter @Setter private String fid;

    @Column(name = "plant")
    @Getter @Setter private String plant;

    @Column(name = "year")
    @Getter @Setter private Integer year;

    @Column(name = "subspecies")
    @Getter @Setter private String subspecies;


}
