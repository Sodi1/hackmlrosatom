package com.rosatom.hackaton.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "workspace_correction")
public class WorkspaceCorrectionEntity {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "good_name")
    private String goodName;
    @Column(name = "short_name")
    private String shortName;
    @ManyToOne
    @JoinColumn(name = "division_id")
    private DivisionEntity divisionEntity;
}
