package com.rosatom.hackaton.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "company_type")
public class CompanyTypeEntity {
    @Id
    private Long id;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "full_name")
    private String fullName;
}
