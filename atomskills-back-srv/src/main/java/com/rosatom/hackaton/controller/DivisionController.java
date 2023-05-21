package com.rosatom.hackaton.controller;

import com.rosatom.hackaton.model.entity.DivisionEntity;
import com.rosatom.hackaton.service.DivisionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/division")
public class DivisionController {

    private final DivisionService divisionService;

    @GetMapping
    public ResponseEntity<List<DivisionEntity>> findAll(){
        return ResponseEntity.ok(divisionService.findAll());
    }



}
