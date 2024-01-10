package com.gim.project.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "species",schema = "public")
public class Species {
    @Id
    @Column(name = "id")
    private Integer id;


}
