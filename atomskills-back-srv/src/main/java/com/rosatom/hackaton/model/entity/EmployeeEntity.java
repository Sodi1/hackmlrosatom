package com.rosatom.hackaton.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "role_in_event")
    private String roleInEvent;

    @Column(name = "competence_list")
    private String competenceList;

    @Column(name = "position")
    private String position;

    @Column(name = "category")
    private String category;

    @Column(name = "employment_start")
    private LocalDate employmentStart;

    @Column(name = "workplace")
    private String workplace;

    @Column(name = "profession")
    private String profession;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "employment_start_at_rosatom")
    private LocalDate employmentStartAtRosatom;

    @Column(name = "education")
    private String education;

    @Column(name = "education_place")
    private String educationPlace;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @Column(name = "specialization")
    private String specialization;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;
}
