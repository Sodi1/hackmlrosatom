package com.rosatom.hackaton.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "division")
public class DivisionEntity {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
}
