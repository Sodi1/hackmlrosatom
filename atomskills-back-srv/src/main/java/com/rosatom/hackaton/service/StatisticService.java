package com.rosatom.hackaton.service;

import com.rosatom.hackaton.model.enums.Gender;
import com.rosatom.hackaton.repository.CompetenceRepository;
import com.rosatom.hackaton.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StatisticService {

    private final EmployeeRepository employeeRepository;
    private final CompetenceRepository competenceRepository;

    public List<Map<Integer, Integer>> groupEmployeeByAge(Gender gender, Integer divisionId, Long qualificationId) {
        return employeeRepository.groupEmployeeByYears(
                Optional.ofNullable(gender).map(Gender::getId).orElse(null),
                divisionId,
                qualificationId);
    }

    public List<Map<Integer, Integer>> groupByCompetence(Gender gender, Integer divisionId, Long qualificationId) {
        return employeeRepository.groupByCompetence(
                Optional.ofNullable(gender).map(Gender::getId).orElse(null),
                divisionId,
                qualificationId
        );
    }

    public List<Map<Integer, Integer>> groupByGender(Integer divisionId, Long qualificationId) {
        return employeeRepository.groupByGender(divisionId, qualificationId);
    }

    public List<Map<String, Integer>> groupPeopleByOrg(Gender gender, Integer divisionId, Long qualificationId) {
        return employeeRepository.groupPeopleByOrg(Optional.ofNullable(gender).map(Gender::getId).orElse(null),
                divisionId, qualificationId);
    }

    public Integer getCountAllPeople() {
        return employeeRepository.getAllCountRows();
    }

    public Integer getCountPeopleWithCompetence() {
        return competenceRepository.getCountPeopleWithCompetence();
    }


    public List<Map<String, Integer>> getTeamCountByQualification(Long qualificationId){
        return competenceRepository.getTeamCountByQualification(qualificationId);
    }
}