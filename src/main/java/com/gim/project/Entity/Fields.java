package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fields",schema = "public")
public class Fields {
    @Id
    @Column(name = "fid")
    @Getter @Setter private Integer id;

    @Column(name = "field_id")
    @Getter @Setter private String fid;

    @Column(name = "soil")
    @Getter @Setter private String soil;

    @Column(name = "srid")
    @Getter @Setter private Integer srid;

    @Column(name = "geometry")
    @Getter @Setter private String geometry;
}
