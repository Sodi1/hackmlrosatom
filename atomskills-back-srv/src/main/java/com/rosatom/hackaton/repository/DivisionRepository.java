package com.rosatom.hackaton.repository;

import com.rosatom.hackaton.model.entity.DivisionEntity;
import com.rosatom.hackaton.model.model.OrganizationPrizesDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DivisionRepository extends CrudRepository<DivisionEntity, Long> {

    List<DivisionEntity> findAll();

    @Query(value = "SELECT * FROM division_prizes", nativeQuery = true)
    List<Map<String, OrganizationPrizesDto>>  getPrizesPlaceByDivision();



}
