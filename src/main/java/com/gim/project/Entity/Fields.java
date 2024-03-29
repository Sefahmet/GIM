package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fields",schema = "public")
public class Fields {
    @Id
    @Column(name = "field_id")
    @Getter @Setter private String fid;

    @Column(name = "soil")
    @Getter @Setter private String soil;

    @Column(name = "srid")
    @Getter @Setter private Integer srid;

    @Column(name = "geometry")
    @Getter @Setter private String geometry;
}
