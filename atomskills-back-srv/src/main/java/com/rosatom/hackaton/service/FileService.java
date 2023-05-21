package com.rosatom.hackaton.service;

import com.rosatom.hackaton.exception.FileNotFoundException;
import com.rosatom.hackaton.model.entity.FileEntity;
import com.rosatom.hackaton.model.enums.FileType;
import com.rosatom.hackaton.repository.CompetenceRepository;
import com.rosatom.hackaton.repository.EmployeeRepository;
import com.rosatom.hackaton.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final CompetenceRepository competenceRepository;
    private final EmployeeRepository employeeRepository;

    public FileEntity save(FileEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }

    @Transactional
    public void delete(Long fileId) {
        Optional<FileEntity> fileEntityOpt = fileRepository.findByFileId(fileId);
        if (fileEntityOpt.isEmpty()) {
            throw new FileNotFoundException("%s file not found".formatted(fileId));
        }
        FileEntity fileEntity = fileEntityOpt.get();
        FileType fileType = fileEntity.getType();
        if (fileType == FileType.COMPETENCE) {
            competenceRepository.deleteAllByFileId(fileId);
        }
        if (fileType == FileType.EMPLOYEE) {
            employeeRepository.deleteByFileId(fileId);
        }
        fileRepository.deleteById(fileId);
    }
}
