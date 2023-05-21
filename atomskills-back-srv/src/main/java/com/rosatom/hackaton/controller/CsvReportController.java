package com.rosatom.hackaton.controller;

import com.rosatom.hackaton.model.entity.FileEntity;
import com.rosatom.hackaton.service.CsvReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/file/csv")
public class CsvReportController {

    private final CsvReportService csvReportService;

    @PostMapping("/employee")
    public ResponseEntity<List<FileEntity>> saveEmployee(@RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(csvReportService.saveEmployee(file));
    }

    @PostMapping("/competence/{qualificationId}")
    public ResponseEntity<List<FileEntity>> saveCompetence(@RequestPart("file") MultipartFile file,
                                                           @PathVariable("qualificationId") Long qualificationId){

        return ResponseEntity.ok(csvReportService.saveCompetence(file, qualificationId));
    }

}
