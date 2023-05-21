package com.rosatom.hackaton.repository;

import com.rosatom.hackaton.model.entity.CompanyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyTypeEntity, Long> {
}
