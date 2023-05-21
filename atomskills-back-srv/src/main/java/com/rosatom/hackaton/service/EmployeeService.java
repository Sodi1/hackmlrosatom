package com.rosatom.hackaton.service;

import com.rosatom.hackaton.model.entity.EmployeeEntity;
import com.rosatom.hackaton.model.model.OrganizationPrizesDto;
import com.rosatom.hackaton.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    @Transactional
    public EmployeeEntity save(EmployeeEntity employeeEntity){
        return repository.save(employeeEntity);
    }

    public List<EmployeeEntity> findAllByFileId(Integer fileId) {
        return repository.findAllByFileId(fileId);
    }

    public List<Map<String, OrganizationPrizesDto>>   getOrganizationPrizes(){
        return repository.getCompanyPrizes();
    }
}
