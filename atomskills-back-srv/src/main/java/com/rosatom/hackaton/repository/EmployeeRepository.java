package com.rosatom.hackaton.repository;

import com.rosatom.hackaton.model.entity.EmployeeEntity;
import com.rosatom.hackaton.model.model.OrganizationPrizesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAllByFileId(Integer fileId);

    @Query(value = """
            SELECT EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM e.date_of_birth) AS age,
                   COUNT(*) AS count
            FROM rsatom.employee e
            JOIN rsatom.workspace_correction wc ON e.workplace = wc.name
            where (:gender IS NULL OR e.gender = :gender)
            AND (:division_id IS NULL OR wc.division_id = :division_id)
             AND (:qualification_id IS NULL OR :qualification_id IN (
                             SELECT c.qualification_id
                             FROM rsatom.competence c
                             WHERE c.user_name = e.full_name
                         )
                     )
            GROUP BY age
            ORDER BY age DESC
            """, nativeQuery = true)
    List<Map<Integer, Integer>> groupEmployeeByYears(@Param("gender") Integer gender,
                                                     @Param("division_id") Integer divisionId,
                                                     @Param("qualification_id") Long qualificationId);

    @Query(value = """
                   select e.competence_list as competence, count(*) as count from rsatom.employee e
                   JOIN rsatom.workspace_correction wc ON e.workplace = wc.name
                   where (:gender IS NULL OR e.gender = :gender)
                   AND (:division_id IS NULL OR wc.division_id = :division_id)
                      AND (:qualification_id IS NULL OR :qualification_id IN (
                             SELECT c.qualification_id
                             FROM rsatom.competence c
                             WHERE c.user_name = e.full_name
                         )
                     )
                       
                   GROUP BY competence
                   ORDER BY count DESC
            """, nativeQuery = true)
    List<Map<Integer, Integer>> groupByCompetence(@Param("gender") Integer gender,
                                                  @Param("division_id") Integer divisionId,
                                                  @Param("qualification_id") Long qualificationId);

    @Query(value = """
                select e.gender as gender, COUNT(*) as count from rsatom.employee e
                JOIN rsatom.workspace_correction wc ON e.workplace = wc.name
                WHERE (:division_id IS NULL OR wc.division_id = :division_id)
                AND (:qualification_id IS NULL OR :qualification_id IN (
                             SELECT c.qualification_id
                             FROM rsatom.competence c
                             WHERE c.user_name = e.full_name
                         )
                     )
                GROUP BY gender
                ORDER BY count DESC
            """, nativeQuery = true)
    List<Map<Integer, Integer>> groupByGender(@Param("division_id") Integer divisionId,
                                              @Param("qualification_id") Long qualificationId);

    @Query(value = """
         SELECT wc.short_name as name, COUNT(e.*) as count
            FROM rsatom.employee e
            JOIN rsatom.workspace_correction wc ON e.workplace = wc.name
            where (:gender IS NULL OR e.gender = :gender)
            AND (:division_id IS NULL OR wc.division_id = :division_id)
            AND (:qualification_id IS NULL OR :qualification_id IN (
                             SELECT c.qualification_id
                             FROM rsatom.competence c
                             WHERE c.user_name = e.full_name
                         )
                     )
            GROUP BY short_name
            ORDER BY count DESC
""",
            nativeQuery = true)
    List<Map<String, Integer>> groupPeopleByOrg(@Param("gender") Integer gender,
                                                @Param("division_id") Integer divisionId,
                                                @Param("qualification_id") Long qualificationId);

    void deleteByFileId(Long fileId);


    @Query("""
SELECT COUNT(*) FROM employee
""")
    Integer getAllCountRows();


    @Query(value = "SELECT * FROM rsatom.organization_prizes", nativeQuery = true)
    List<Map<String, OrganizationPrizesDto>>  getCompanyPrizes();
}
