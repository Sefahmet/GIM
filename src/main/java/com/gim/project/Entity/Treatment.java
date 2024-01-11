package com.gim.project.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "treatments",schema = "public")
public class Treatment {
    @Id
    @Column(name = "id")
    @Getter @Setter private Integer id;

    @Column(name = "field_id")
    @Getter @Setter private String fid;

    @Column(name = "date")
    @Getter @Setter private String date;

    @Column(name = "treatment")
    @Getter @Setter private String treatment;

    @Column(name = "treatment_type")
    @Getter @Setter private String treatment_type;

}
