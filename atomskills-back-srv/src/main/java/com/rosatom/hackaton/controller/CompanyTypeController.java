package com.rosatom.hackaton.controller;

import com.rosatom.hackaton.model.entity.CompanyTypeEntity;
import com.rosatom.hackaton.service.CompanyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/company-type")
public class CompanyTypeController {

    private final CompanyTypeService companyTypeService;

    @GetMapping("/")
    public List<CompanyTypeEntity> findAll(){
        return companyTypeService.findAll();
    }

}
