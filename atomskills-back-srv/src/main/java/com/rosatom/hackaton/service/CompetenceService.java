package com.rosatom.hackaton.service;

import com.rosatom.hackaton.model.entity.CompetenceEntity;
import com.rosatom.hackaton.repository.CompetenceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CompetenceService {

    private final CompetenceRepository repository;

    public void save(CompetenceEntity entity){
        repository.save(entity);
    }

}
