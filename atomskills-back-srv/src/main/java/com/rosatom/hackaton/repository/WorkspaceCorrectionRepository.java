package com.rosatom.hackaton.repository;

import com.rosatom.hackaton.model.entity.WorkspaceCorrectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceCorrectionRepository extends JpaRepository<WorkspaceCorrectionEntity, Long> {
}
