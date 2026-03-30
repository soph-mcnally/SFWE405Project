package SFWE405.Project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.Project.entity.Enrollment;
import SFWE405.Project.entity.People;
import SFWE405.Project.service.AcademicRecordService;
import SFWE405.Project.service.AuthenticationService;

/**
 * @author Brandon Sisco
 *
 * REST controller for handling academic record requests.
 *
 * This controller validates the user's bearer token and returns
 * the academic record associated with the authenticated student.
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
    public ResponseEntity<List<Enrollment>> getAcademicRecord(
            @RequestHeader("Authorization") String authHeader) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can view academic records");
        }

        List<Enrollment> academicRecord =
                academicRecordService.getAcademicRecord(person.getPersonID());

        return ResponseEntity.ok(academicRecord);
    }
}
