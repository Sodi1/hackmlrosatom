package com.rosatom.hackaton.controller;

import com.rosatom.hackaton.model.entity.QualificationEntity;
import com.rosatom.hackaton.model.enums.QualificationType;
import com.rosatom.hackaton.service.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/qualification")
public class QualificationController {

    private final QualificationService qualificationService;

    @GetMapping
    public ResponseEntity<List<QualificationEntity>> findAll() {
        return ResponseEntity.ok(qualificationService.findAllOrderByNameAsc());
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<QualificationEntity>> findByType(@PathVariable("type") QualificationType type) {
        List<QualificationEntity> qualificationEntities = qualificationService.findByType(type);
        if (qualificationEntities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(qualificationEntities);
    }

}
