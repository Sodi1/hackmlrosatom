package com.rosatom.hackaton.service;

import com.rosatom.hackaton.repository.WorkspaceCorrectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkspaceCorrectionService {
    private final WorkspaceCorrectionRepository repository;
}
