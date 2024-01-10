package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gardener",schema = "ex2")
public class Gardener {

    @Id
    @Column(name = "id")
    @Getter @Setter private Integer id;

    @Column(name = "name")
    @Getter @Setter private String name;

}
