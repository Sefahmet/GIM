package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "activity",schema = "ex2")

public class Activity {

    @Id
    @Column(name = "id")
    @Getter @Setter private Long id;
    @Column(name = "name")
    @Getter @Setter private String name;
    @Column(name = "gardener")
    @Getter @Setter private Integer gardener;
    @Column(name = "section")
    @Getter @Setter private Integer section;
    @Column(name = "duration")
    @Getter @Setter private Integer duration;



}
