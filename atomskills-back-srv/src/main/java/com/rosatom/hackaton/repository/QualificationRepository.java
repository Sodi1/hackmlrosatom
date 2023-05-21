package com.rosatom.hackaton.repository;

import com.rosatom.hackaton.model.entity.QualificationEntity;
import com.rosatom.hackaton.model.enums.QualificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRepository extends JpaRepository<QualificationEntity, Long> {

    @Query("""
 from qualification order by name ASC
""")
    List<QualificationEntity> findAllOrderByNameAsc();

    List<QualificationEntity> findAllByType(QualificationType type);

}
