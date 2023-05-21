package com.rosatom.hackaton.service;

import com.rosatom.hackaton.model.entity.DivisionEntity;
import com.rosatom.hackaton.model.model.OrganizationPrizesDto;
import com.rosatom.hackaton.repository.DivisionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class DivisionService {

    private final DivisionRepository repository;

    public List<DivisionEntity> findAll(){
        return repository.findAll();
    }

    public List<Map<String, OrganizationPrizesDto>>  getPrizesPlaceByDivision(){
        return repository.getPrizesPlaceByDivision();
    }


}
