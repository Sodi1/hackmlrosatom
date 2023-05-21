package com.rosatom.hackaton.model.entity;

import com.rosatom.hackaton.model.enums.QualificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "qualification")
public class QualificationEntity {

    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private QualificationType type;
}
