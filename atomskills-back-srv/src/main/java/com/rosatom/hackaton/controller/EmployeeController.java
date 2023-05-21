package com.rosatom.hackaton.controller;

import com.rosatom.hackaton.model.entity.EmployeeEntity;
import com.rosatom.hackaton.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{fileId}")
    public List<EmployeeEntity> findByFileId(@PathVariable("fileId") Integer fileId){
        return employeeService.findAllByFileId(fileId);
    }


}
