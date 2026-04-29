package SFWE405.Project.controller;

import java.util.List;

import SFWE405.Project.entity.CourseAssignment;
import SFWE405.Project.repository.CourseAssignmentRepository;
import SFWE405.Project.repository.PeopleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.Project.dto.AvailableSemesterResponse;
import SFWE405.Project.entity.Course;
import SFWE405.Project.entity.People;
import SFWE405.Project.entity.Semester;
import SFWE405.Project.service.AuthenticationService;
import SFWE405.Project.service.SemesterService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/semester")
public class SemesterController {
    private final SemesterService semesterService;
    private final AuthenticationService authenticationService;
    private final CourseAssignmentRepository courseAssignmentRepository;
    private final PeopleRepository peopleRepository;

    public SemesterController(SemesterService semesterService,
                              AuthenticationService authenticationService,
                              CourseAssignmentRepository courseAssignmentRepository,
                              PeopleRepository peopleRepository) {
        this.semesterService = semesterService;
        this.authenticationService = authenticationService;
        this.courseAssignmentRepository = courseAssignmentRepository;
        this.peopleRepository = peopleRepository;
    }

    // semester CRUD
    // get all semesters
    @GetMapping
    public ResponseEntity<List<AvailableSemesterResponse>> getAllSemesters(
            @RequestHeader("Authorization") String authHeader) {
        authenticationService.validateToken(authHeader);
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

    // get semester by id
    @GetMapping("/{id}")
    public ResponseEntity<AvailableSemesterResponse> getSemesterById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        authenticationService.validateToken(authHeader);
        return ResponseEntity.ok(semesterService.getSemesterById(id));
    }

    // create new semester
    @PostMapping
    public ResponseEntity<AvailableSemesterResponse> createSemester(
            @RequestBody Semester semester,
            @RequestHeader("Authorization") String authHeader) {
        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.ADMIN) {
            throw new RuntimeException("Only admin can create semesters");
        }
        return ResponseEntity.ok(semesterService.createSemester(semester));
    }

    // update semester
    @PutMapping("/{id}")
    public ResponseEntity<AvailableSemesterResponse> updateSemester(
            @PathVariable Long id,
            @RequestBody Semester semester,
            @RequestHeader("Authorization") String authHeader) {
        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.ADMIN) {
            throw new RuntimeException("Only admin can update semesters");
        }
        return ResponseEntity.ok(semesterService.updateSemester(id, semester));
    }

    // delete semester
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.ADMIN) {
            throw new RuntimeException("Only admin can delete semesters");
        }
        semesterService.deleteSemester(id);
        return ResponseEntity.noContent().build();

    }

    // manage courses by semester
    @GetMapping("/{semesterId}/courses")
    public ResponseEntity<List<Course>> getCoursesBySemester(
            @PathVariable Long semesterId,
            @RequestHeader("Authorization") String authHeader) {
        authenticationService.validateToken(authHeader);
        return ResponseEntity.ok(semesterService.getCoursesBySemester(semesterId));
    }

    @PostMapping("/{semesterId}/courses")
    public ResponseEntity<Course> addCourseToSemester(
            @PathVariable Long semesterId,
            @RequestBody Course course,
            @RequestHeader("Authorization") String authHeader) {
        People person = authenticationService.validateToken(authHeader);
        if (person.getPersonType() != People.PersonType.ADMIN) {
            throw new RuntimeException("Only admin can add courses");
        }
        return ResponseEntity.ok(semesterService.addCourseToSemester(semesterId, course));
    }

    @PutMapping("/{semesterId}/courses/{courseId}")
    public ResponseEntity<Course> updateCourseInSemester(
            @PathVariable Long semesterId,
            @PathVariable Long courseId,
            @RequestBody Course course,
            @RequestHeader("Authorization") String authHeader) {
        People person = authenticationService.validateToken(authHeader);
        if (person.getPersonType() != People.PersonType.ADMIN) {
            throw new RuntimeException("Only admin can update courses");
        }
        return ResponseEntity.ok(semesterService.updateCourseInSemester(semesterId, courseId, course));
    }

    @DeleteMapping("/{semesterId}/courses/{courseId}")
    public ResponseEntity<Void> deleteCourseFromSemester(
            @PathVariable Long semesterId,
            @PathVariable Long courseId,
            @RequestHeader("Authorization") String authHeader){
        People person = authenticationService.validateToken(authHeader);
        if (person.getPersonType() != People.PersonType.ADMIN){
            throw new RuntimeException("Only admin can delete courses");
        }
        semesterService.deleteCourseFromSemester(semesterId, courseId);
        return ResponseEntity.noContent().build();
    }

    // get all faculty
    @GetMapping("/faculty")
    public ResponseEntity<List<People>> getAllFaculty(
            @RequestHeader("Authorization") String authHeader) {
        authenticationService.validateToken(authHeader);
        List<People> faculty = peopleRepository.findAll()
                .stream()
                .filter(p -> p.getPersonType() == People.PersonType.FACULTY)
                .toList();
        return ResponseEntity.ok(faculty);
    }

    // get faculty assigned to a course
    @GetMapping("/{semesterId}/courses/{courseId}/faculty")
    public ResponseEntity<List<People>> getFacultyForCourse(
            @PathVariable Long semesterId,
            @PathVariable Long courseId,
            @RequestHeader("Authorization") String authHeader) {
        authenticationService.validateToken(authHeader);
        List<People> faculty = courseAssignmentRepository
                .findByCourse_CourseId(courseId)
                .stream()
                .map(CourseAssignment::getFaculty)
                .toList();
        return ResponseEntity.ok(faculty);
    }

    // assign faculty to a course
    @PostMapping("/{semesterId}/courses/{courseId}/faculty/{personId}")
    public ResponseEntity<Void> assignFacultyToCourse(
            @PathVariable Long semesterId,
            @PathVariable Long courseId,
            @PathVariable Long personId,
            @RequestHeader("Authorization") String authHeader) {
        People person = authenticationService.validateToken(authHeader);
        if (person.getPersonType() != People.PersonType.ADMIN) {
            throw new RuntimeException("Only admin can assign faculty");
        }
        if (courseAssignmentRepository.existsByFaculty_PersonIDAndCourse_CourseId(personId, courseId)) {
            throw new RuntimeException("Faculty already assigned to this course");
        }
        People faculty = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        Course course = semesterService.getCourseById(courseId);
        courseAssignmentRepository.save(new CourseAssignment(faculty, course));
        return ResponseEntity.ok().build();
    }

    // remove faculty from a course
    @DeleteMapping("/{semesterId}/courses/{courseId}/faculty/{personId}")
    public ResponseEntity<Void> removeFacultyFromCourse(
            @PathVariable Long semesterId,
            @PathVariable Long courseId,
            @PathVariable Long personId,
            @RequestHeader("Authorization") String authHeader) {
        People person = authenticationService.validateToken(authHeader);
        if (person.getPersonType() != People.PersonType.ADMIN) {
            throw new RuntimeException("Only admin can remove faculty");
        }
        courseAssignmentRepository.deleteByFaculty_PersonIDAndCourse_CourseId(personId, courseId);
        return ResponseEntity.noContent().build();
    }


}

