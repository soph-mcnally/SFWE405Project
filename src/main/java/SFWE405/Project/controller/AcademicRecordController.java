package SFWE405.Project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.Project.dto.AcademicRecordItemResponse;
import SFWE405.Project.entity.Enrollment;
import SFWE405.Project.entity.People;
import SFWE405.Project.service.AcademicRecordService;
import SFWE405.Project.service.AuthenticationService;

/**
 * REST controller for handling academic record requests.
 *
 * This controller validates the user's bearer token and returns
 * academic record data associated with the authenticated student.
 */
@RestController
@RequestMapping("/api/academic-record")
public class AcademicRecordController {

    private final AuthenticationService authenticationService;
    private final AcademicRecordService academicRecordService;

    public AcademicRecordController(AuthenticationService authenticationService,
                                    AcademicRecordService academicRecordService) {
        this.authenticationService = authenticationService;
        this.academicRecordService = academicRecordService;
    }

    @GetMapping
    public ResponseEntity<List<AcademicRecordItemResponse>> getAcademicRecord(
            @RequestHeader("Authorization") String authHeader) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can view academic records");
        }

        List<Enrollment> academicRecord =
                academicRecordService.getAcademicRecord(person.getPersonID());

        List<AcademicRecordItemResponse> response = academicRecord.stream().map(enrollment -> {
            AcademicRecordItemResponse item = new AcademicRecordItemResponse();
            item.setEnrollmentId(enrollment.getEnrollmentId());
            item.setCourseCode(enrollment.getCourse().getCourseCode());
            item.setCourseName(enrollment.getCourse().getCourseName());
            item.setCourseType(enrollment.getCourse().getCourseType().name());
            item.setUnitsAmount(enrollment.getCourse().getUnitsAmount());
            item.setSemester(
                    enrollment.getCourse().getSemester().getSeason().name()
                            + " "
                            + enrollment.getCourse().getSemester().getSemesterYear()
            );
            item.setGrade(enrollment.getGrade());
            item.setStatus(enrollment.getStatus().name());
            return item;
        }).toList();

        return ResponseEntity.ok(response);
    }
}