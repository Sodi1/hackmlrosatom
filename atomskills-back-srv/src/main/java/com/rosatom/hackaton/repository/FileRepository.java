package com.rosatom.hackaton.repository;

import com.rosatom.hackaton.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    @Query("""
    from file WHERE id = :id
""")
    Optional<FileEntity> findByFileId(@Param("id") Long id);
}
