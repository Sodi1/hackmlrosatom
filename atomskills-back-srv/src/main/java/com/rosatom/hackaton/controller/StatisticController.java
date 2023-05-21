package com.rosatom.hackaton.controller;

import com.rosatom.hackaton.model.enums.Gender;
import com.rosatom.hackaton.model.model.OrganizationPrizesDto;
import com.rosatom.hackaton.service.DivisionService;
import com.rosatom.hackaton.service.EmployeeService;
import com.rosatom.hackaton.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/statistic")
public class StatisticController {

    private final StatisticService statisticService;
    private final EmployeeService employeeService;
    private final DivisionService divisionService;

    @GetMapping("/employeeByAge")
    public List<Map<Integer, Integer>> groupEmployeeByAge(@RequestParam(value = "gender", required = false) Gender gender,
                                                          @RequestParam(value = "divisionId", required = false) Integer divisionId,
                                                          @RequestParam(value = "qualificationId", required = false) Long qualificationId) {
        return statisticService.groupEmployeeByAge(gender, divisionId, qualificationId);
    }

    @GetMapping("/groupByCompetence")
    public List<Map<Integer, Integer>> groupByCompetence(@RequestParam(value = "gender", required = false) Gender gender,
                                                         @RequestParam(value = "divisionId", required = false) Integer divisionId,
                                                         @RequestParam(value = "qualificationId", required = false) Long qualificationId) {
        return statisticService.groupByCompetence(gender, divisionId, qualificationId);
    }

    @GetMapping("/groupByGender")
    public List<Map<Integer, Integer>> groupByGender(@RequestParam(value = "divisionId", required = false) Integer divisionId,
                                                     @RequestParam(value = "qualificationId", required = false) Long qualificationId) {
        return statisticService.groupByGender(divisionId, qualificationId);
    }

    @GetMapping("/groupPeopleByOrg")
    public List<Map<String, Integer>> groupPeopleByOrg(@RequestParam(value = "gender", required = false) Gender gender,
                                                       @RequestParam(value = "divisionId", required = false) Integer divisionId,
                                                       @RequestParam(value = "qualificationId", required = false) Long qualificationId){
        return statisticService.groupPeopleByOrg(gender, divisionId, qualificationId);
    }


    @GetMapping("/countAllPeople")
    public Integer getCountAllPeople(){
        return statisticService.getCountAllPeople();
    }

    @GetMapping("/countPeopleWithCompetence")
    public Integer getCountPeopleWithCompetence(){
        return statisticService.getCountPeopleWithCompetence();
    }


    @GetMapping("/getTeamCountByQualification")
    public List<Map<String, Integer>> getTeamCountByQualification(@RequestParam(value = "qualificationId", required = false) Long qualificationId){
        return statisticService.getTeamCountByQualification(qualificationId);
    }

    @GetMapping("/prizes/company")
    public List<Map<String, OrganizationPrizesDto>> getCompanyPrizes(){
        return employeeService.getOrganizationPrizes();
    }

    @GetMapping("/prizes/division")
    public List<Map<String, OrganizationPrizesDto>> getTopDivision(){
        return divisionService.getPrizesPlaceByDivision();
    }
}
