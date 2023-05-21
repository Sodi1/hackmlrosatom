package com.rosatom.hackaton.repository;

import com.rosatom.hackaton.model.entity.CompetenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CompetenceRepository extends JpaRepository<CompetenceEntity, Long> {

    void deleteAllByFileId(Long fileId);

    @Query("""
SELECT COUNT(DISTINCT c.userName) FROM competence c
""")
    Integer getCountPeopleWithCompetence();

    @Query("""
    SELECT q.name as name, COUNT(*) as count
        FROM competence c
        JOIN qualification q ON q.id = c.qualification.id
        WHERE c.isTeam = true AND
        (:qualification_id IS NULL OR q.id = :qualification_id)
        GROUP BY q.name
        ORDER BY count DESC
""")
    List<Map<String, Integer>> getTeamCountByQualification(@Param("qualification_id") Long qualificationId);
}
