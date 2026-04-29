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
import SFWE405.Project.repository.CourseAssignmentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
@CrossOrigin(origins = "http://localhost:3000")
public class EnrollCoursesController {

    private final AuthenticationService authenticationService;
    private final EnrollmentService enrollmentService;
    private final CourseAssignmentRepository courseAssignmentRepository;

    public EnrollCoursesController(AuthenticationService authenticationService,
                                   EnrollmentService enrollmentService, CourseAssignmentRepository courseAssignmentRepository) {
        this.authenticationService = authenticationService;
        this.enrollmentService = enrollmentService;
        this.courseAssignmentRepository = courseAssignmentRepository;
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
            @PathVariable Long semesterId,
            @RequestParam(required = false) String search) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can enroll in courses");
        }

        List<Course> courses = enrollmentService.getCoursesForSemester(
                person.getPersonID(), semesterId, search);

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

    @GetMapping("/my-courses")
    public ResponseEntity<List<AvailableCourseResponse>> getMyEnrolledCourses(
            @RequestHeader("Authorization") String authHeader) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can view enrolled courses");
        }

        List<Course> courses = enrollmentService.getEnrolledCourses(person.getPersonID());

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

    @GetMapping("/completed-courses")
    public ResponseEntity<List<AvailableCourseResponse>> getMyCompletedCourses(
            @RequestHeader("Authorization") String authHeader) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can view completed courses");
        }

        List<Course> courses = enrollmentService.getCompletedCourses(person.getPersonID());

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

    //Faculty - Get courses they are assigned to teach ; @TravisPotter
    @GetMapping("/my-assigned-courses")
    public ResponseEntity<List<Course>> getAssignedCourses(
    @RequestHeader("Authorization") String authHeader) {
        People faculty = authenticationService.validateToken(authHeader);
        if (faculty.getPersonType() != People.PersonType.FACULTY) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Faculty allowed");
        }

        List<Course> courses = courseAssignmentRepository.findCoursesByFacultyPersonID(faculty.getPersonID());

        return ResponseEntity.ok(courses);
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

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> unenrollStudentFromCourse(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long courseId) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new RuntimeException("Only students can drop courses");
        }

        enrollmentService.unenrollStudentFromCourse(person.getPersonID(), courseId);

        return ResponseEntity.noContent().build();
    }
}
