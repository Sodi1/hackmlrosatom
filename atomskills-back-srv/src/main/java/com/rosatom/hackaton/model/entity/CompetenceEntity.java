package com.rosatom.hackaton.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "competence")
public class CompetenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;
    @ManyToOne
    @JoinColumn(name = "qualification_id")
    private QualificationEntity qualification;
    @Column(name = "data")
    private String data;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "is_team")
    private Boolean isTeam;
    @Column(name = "teams")
    private String teams;
}
