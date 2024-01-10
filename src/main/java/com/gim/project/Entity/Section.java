package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "section",schema = "ex2")
public class Section {
    @Id
    @Column(name ="id")
    @Getter @Setter private Integer id;

    @Column(name = "name")
    @Getter @Setter private String name;

    @Column(name ="planted")
    @Getter @Setter private Integer planted;

    @Column(name = "srid")
    @Getter @Setter private Integer srid;

    @Column(name = "geometry")
    @Getter @Setter private String geometry;
}
