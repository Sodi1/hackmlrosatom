package com.rosatom.hackaton.controller;

import com.rosatom.hackaton.model.entity.FileEntity;
import com.rosatom.hackaton.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;

    @GetMapping
    public List<FileEntity> findAll() {
        return fileService.findAll();
    }

    @DeleteMapping("/{fileId}")
    public void delete(@PathVariable Long fileId) {
        fileService.delete(fileId);
    }

}
