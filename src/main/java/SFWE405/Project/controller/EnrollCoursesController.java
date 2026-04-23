package SFWE405.Project.controller;

import SFWE405.Project.dto.AvailableCourseResponse;
import SFWE405.Project.dto.AvailableSemesterResponse;
import SFWE405.Project.dto.EnrollCoursesRequest;
import SFWE405.Project.dto.EnrollCoursesResponse;
import SFWE405.Project.entity.Course;
import SFWE405.Project.entity.People;
import SFWE405.Project.entity.Semester;
import SFWE405.Project.service.AuthenticationService;
import SFWE405.Project.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
@CrossOrigin(origins = "http://localhost:3000")
public class EnrollCoursesController {

    private final AuthenticationService authenticationService;
    private final EnrollmentService enrollmentService;

    public EnrollCoursesController(AuthenticationService authenticationService,
                                   EnrollmentService enrollmentService) {
        this.authenticationService = authenticationService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/semesters")
    public ResponseEntity<List<AvailableSemesterResponse>> getAvailableSemesters(
            @RequestHeader("Authorization") String authHeader) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can enroll in courses");
        }

        List<Semester> semesters = enrollmentService.getAvailableSemesters(person.getPersonID());

        List<AvailableSemesterResponse> response = semesters.stream().map(semester -> {
            AvailableSemesterResponse item = new AvailableSemesterResponse();
            item.setId(semester.getSemesterId());
            item.setSeason(semester.getSeason().name());
            item.setSemesterYear(semester.getSemesterYear());
            return item;
        }).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/semesters/{semesterId}/courses")
    public ResponseEntity<List<AvailableCourseResponse>> getCoursesForSemester(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long semesterId) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can enroll in courses");
        }

        List<Course> courses = enrollmentService.getCoursesForSemester(
                person.getPersonID(), semesterId);

        List<AvailableCourseResponse> response = courses.stream().map(course -> {
            AvailableCourseResponse item = new AvailableCourseResponse();
            item.setCourseId(course.getCourseId());
            item.setCourseCode(course.getCourseCode());
            item.setCourseName(course.getCourseName());
            item.setCourseType(course.getCourseType().name());
            item.setUnitsAmount(course.getUnitsAmount());
            item.setUpperDivision(course.getUpperDivision());
            item.setSemester(
                    course.getSemester().getSeason().name()
                            + " "
                            + course.getSemester().getSemesterYear()
            );
            return item;
        }).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<EnrollCoursesResponse> enrollStudentInCourses(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody EnrollCoursesRequest request) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can enroll in courses");
        }

        EnrollCoursesResponse response = enrollmentService.enrollStudentInCourses(
                person.getPersonID(), request.getCourseIds());

        return ResponseEntity.ok(response);
    }
}
