package com.rosatom.hackaton.service;

import com.rosatom.hackaton.model.entity.QualificationEntity;
import com.rosatom.hackaton.model.enums.QualificationType;
import com.rosatom.hackaton.repository.QualificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QualificationService {

    private final QualificationRepository qualificationRepository;

    public List<QualificationEntity> findAll(){
        return qualificationRepository.findAll();
    }

    public List<QualificationEntity> findByType(QualificationType type) {
        return qualificationRepository.findAllByType(type);
    }

    public List<QualificationEntity> findAllOrderByNameAsc() {
        return qualificationRepository.findAllOrderByNameAsc();
    }
}
