package com.rosatom.hackaton.service;

import com.opencsv.CSVReader;
import com.rosatom.hackaton.exception.ErrorSaveCsv;
import com.rosatom.hackaton.exception.QualificationNotFound;
import com.rosatom.hackaton.model.entity.CompetenceEntity;
import com.rosatom.hackaton.model.entity.EmployeeEntity;
import com.rosatom.hackaton.model.entity.FileEntity;
import com.rosatom.hackaton.model.entity.QualificationEntity;
import com.rosatom.hackaton.model.enums.FileType;
import com.rosatom.hackaton.model.model.ClusterizationRequest;
import com.rosatom.hackaton.repository.QualificationRepository;
import com.rosatom.hackaton.service.integration.ClusterApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class CsvReportService {

    private final EmployeeService employeeService;
    private final FileService fileService;
    private final CompetenceService competenceService;
    private final QualificationRepository qualificationRepository;
    private final ClusterApiService clusterApiService;

    public List<FileEntity> saveEmployee(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;
            boolean isFirstLine = true;
            String fileName = file.getOriginalFilename();
            List<Long> fileIds = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            FileEntity createdFile = fileService.save(new FileEntity(null, fileName, LocalDateTime.now(), FileType.EMPLOYEE, file.getSize()));

            while ((nextLine = reader.readNext()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Пропустить первую строку, если она содержит заголовки столбцов
                }

                // Разбор данных из строки CSV
                String fullName = nextLine[0];
                String gender = nextLine[1];
                String role = nextLine[2];
                String competenceList = nextLine[3];
                String position = nextLine[4];
                String category = nextLine[5];
                String employmentStart = nextLine[6];
                String workplace = nextLine[7];
                String profession = nextLine[8];
                String dateOfBirth = nextLine[9];
                String employmentStartAtAtomSkills = nextLine[10];
                String education = nextLine[11];
                String educationPlace = nextLine[12];
                String graduationYear = nextLine[13];
                String specialization = nextLine[14];

                var employeeEntity = new EmployeeEntity();
                employeeEntity.setFullName(fullName);
                employeeEntity.setGender(Optional.of(gender).filter(this::isConvertibleToInt).map(Integer::parseInt).orElse(null));
                employeeEntity.setRoleInEvent(role);
                employeeEntity.setPosition(position);
                employeeEntity.setCompetenceList(Optional.of(competenceList).filter(Objects::nonNull).map(t -> t.replaceAll(";", "").trim()).orElse(null));
                employeeEntity.setCategory(category);
                employeeEntity.setEmploymentStart(Optional.of(employmentStart).filter(t -> isConvertibleToLocalDate(t, formatter)).map(t -> LocalDate.parse(t, formatter)).orElse(null));
                employeeEntity.setWorkplace(Optional.of(workplace).filter(Objects::nonNull).map(t -> t.replaceAll("\"", "")).orElse(null));
                employeeEntity.setProfession(profession);
                employeeEntity.setDateOfBirth(Optional.of(dateOfBirth).filter(t -> isConvertibleToLocalDate(t, formatter)).map(t -> LocalDate.parse(t, formatter)).orElse(null));
                employeeEntity.setEmploymentStartAtRosatom(Optional.of(employmentStartAtAtomSkills).filter(t -> isConvertibleToLocalDate(t, formatter)).map(t -> LocalDate.parse(t, formatter)).orElse(null));
                employeeEntity.setEducation(education);
                employeeEntity.setEducationPlace(educationPlace);
                employeeEntity.setGraduationYear(Optional.of(graduationYear).filter(this::isConvertibleToInt).map(Integer::parseInt).orElse(null));
                employeeEntity.setSpecialization(specialization);
                employeeEntity.setFile(createdFile);
                employeeService.save(employeeEntity);
                fileIds.add(createdFile.getId());
            }

            clusterApiService.sendClusterization(new ClusterizationRequest(fileIds));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ErrorSaveCsv(e.getMessage());
        }
        return fileService.findAll();
    }

    public List<FileEntity> saveCompetence(MultipartFile file, Long qualificationId) {
        QualificationEntity qualification = qualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new QualificationNotFound("%s not found".formatted(qualificationId)));
        String fileName = file.getOriginalFilename();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] headers = reader.readNext(); // Чтение заголовков столбцов

            String[] rowData;

            var fileEntity = fileService.save(new FileEntity(null, fileName, LocalDateTime.now(), FileType.COMPETENCE, file.getSize()));
            while ((rowData = reader.readNext()) != null) {
                var jsonObject = new JSONObject(); // Создание JSONObject для текущей строки

                // Преобразование данных в JSON, соответствуя заголовкам столбцов
                for (int i = 0; i < headers.length; i++) {
                    String field = headers[i];
                    String value = (i < rowData.length) ? rowData[i] : "";
                    jsonObject.put(field, value);
                }

                var competenceEntity = new CompetenceEntity();
                try {
                    competenceEntity.setUserName(String.valueOf(jsonObject.get("ФИО")));
                    competenceEntity.setIsTeam(false);
                    competenceEntity.setData(jsonObject.toString());
                    competenceEntity.setFile(fileEntity);
                    competenceEntity.setQualification(qualification);
                    competenceEntity.setCreatedAt(LocalDateTime.now());
                    competenceService.save(competenceEntity);
                } catch (JSONException ex) {
                    String fullNames = String.valueOf(jsonObject.get("ФИО участников"));
                    String[] fullNamesArr = fullNames.split("; ");
                    for(String fullName : fullNamesArr){
                        competenceEntity.setUserName(fullName);
                        competenceEntity.setIsTeam(false);
                        competenceEntity.setData(jsonObject.toString());
                        competenceEntity.setFile(fileEntity);
                        competenceEntity.setQualification(qualification);
                        competenceEntity.setCreatedAt(LocalDateTime.now());
                        competenceEntity.setIsTeam(true);
                        competenceEntity.setTeams(fullNames);
                        competenceService.save(competenceEntity);
                    }
                }

                competenceService.save(competenceEntity);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ErrorSaveCsv(e.getMessage());
        }
        return fileService.findAll();
    }

    public boolean isConvertibleToInt(String str) {
        try {
            Integer.parseInt(str);
            return true; // Если успешно преобразовано в int
        } catch (NumberFormatException e) {
            return false; // Если не удалось преобразовать в int
        }
    }

    public static boolean isConvertibleToLocalDate(String str, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(str, formatter);
            return true; // Если успешно преобразовано в LocalDate
        } catch (DateTimeParseException e) {
            return false; // Если не удалось преобразовать в LocalDate
        }
    }
}
