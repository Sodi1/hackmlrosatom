package com.rosatom.hackaton.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "company")
public class CompanyEntity {
    @Id
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "short_name")
    private String shortName;

}
