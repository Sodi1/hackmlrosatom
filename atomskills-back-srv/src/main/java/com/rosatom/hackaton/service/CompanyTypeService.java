package com.rosatom.hackaton.service;

import com.rosatom.hackaton.model.entity.CompanyTypeEntity;
import com.rosatom.hackaton.repository.CompanyTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyTypeService {
    private final CompanyTypeRepository companyTypeRepository;

    public List<CompanyTypeEntity> findAll() {
        return companyTypeRepository.findAll();
    }
}
