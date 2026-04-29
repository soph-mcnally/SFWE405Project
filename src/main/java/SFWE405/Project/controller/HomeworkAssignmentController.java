/*
 * HomeworkAssignmentController.java
 *Last Update: 2026-04-07
 * 
 * Primary Author: @TravisPotter
 * Secondary Author: @N/A
 * 
 * 
 * Controller for handling homework assignment related endpoints.
 * Provides functionality for students to view homework assignments for their courses.
 * 
 */

package SFWE405.Project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import SFWE405.Project.entity.HomeworkAssignment;
import SFWE405.Project.entity.People;
import SFWE405.Project.repository.CourseAssignmentRepository;
import SFWE405.Project.service.AuthenticationService;
import SFWE405.Project.service.HomeworkAssignmentService;



@RestController
@RequestMapping("/api/homework-assignment")
@CrossOrigin(origins = "http://localhost:3000")
public class HomeworkAssignmentController {
    
    private final AuthenticationService authenticationService;
    private final HomeworkAssignmentService homeworkAssignmentService;
    private final CourseAssignmentRepository courseAsgnRepo;

    // Constructor injection for services
    public HomeworkAssignmentController(AuthenticationService authenticationService, HomeworkAssignmentService homeworkAssignmentService, CourseAssignmentRepository courseAsgnRepo) {
        this.authenticationService = authenticationService;
        this.homeworkAssignmentService = homeworkAssignmentService;
        this.courseAsgnRepo = courseAsgnRepo;
    }

    //Student - View HW of course
    @GetMapping("/studentViewHWByCourse/{courseId}")
    public ResponseEntity<List<HomeworkAssignment>>  getHomeworkAssignmentsForStudent(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long courseId)
    {               

        People person = authenticationService.validateToken(authHeader);                //Validate token and get person details

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only students allowed");
        }

        List<HomeworkAssignment> homeworkAssignments = homeworkAssignmentService.getHomeworkForCourseIfEnrolled(person.getPersonID(), courseId); //Get HW assignments for course if student is enrolled

        if(homeworkAssignments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return "204 No Content" if no assignments found
        }

        return ResponseEntity.ok(homeworkAssignments);
    }

    @GetMapping("/my-assignments")
    public ResponseEntity<List<HomeworkAssignment>> getMyHomeworkAssignments(
            @RequestHeader("Authorization") String authHeader) {

        People person = authenticationService.validateToken(authHeader);

        if (person.getPersonType() != People.PersonType.STUDENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only students allowed");
        }

        List<HomeworkAssignment> assignments =
                homeworkAssignmentService.getHomeworkForEnrolledCourses(person.getPersonID());

        if (assignments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(assignments);
    }

    //Faculty View HW of course
    @GetMapping("/facultyViewAssignmentByCourse/{courseId}")
    public ResponseEntity<List<HomeworkAssignment>> getHomeworkAssignmentsForTeacher(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long courseId)
    {
        People faculty = authenticationService.validateToken(authHeader);
        if (faculty.getPersonType() != People.PersonType.FACULTY) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Faculty allowed");
        }

        List<HomeworkAssignment> homeworkAssignments = homeworkAssignmentService.getAllHomeworkForCourseifTeacherAssociated(faculty.getPersonID(), courseId); //Return List of HW assignments currently associated with course
        
        if(homeworkAssignments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return "204 No Content" if no assignments found
        }

        return ResponseEntity.ok(homeworkAssignments);
    }

    //Faculty - Create HW for course
    //TODO: Catch duplicates
    @PostMapping("/facultyCreateAssignmentByCourse/{courseId}")
    public ResponseEntity<HomeworkAssignment> createAssignmentByCourse(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long courseId,
        @RequestBody HomeworkAssignment newAssignment)
    {
        People faculty = authenticationService.validateToken(authHeader);
        if (faculty.getPersonType() != People.PersonType.FACULTY) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Faculty allowed");
        }

        boolean facultyAssociated = courseAsgnRepo.existsByFaculty_PersonIDAndCourse_CourseId(faculty.getPersonID(), courseId);
        if (!facultyAssociated) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Faculty is not associated with specified course");
        }

        HomeworkAssignment createdAssignment = homeworkAssignmentService.createAssignmentForCourse(courseId, newAssignment);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
    }


    //TODO: Faculty - Update Assignment


    //Faculty - Delete Assignment
    @DeleteMapping("/facultyDeleteAssignment/{courseId}/{asgnId}")
    public ResponseEntity<String> deleteAssignment(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long courseId,
        @PathVariable Long asgnId)
    {
        People faculty = authenticationService.validateToken(authHeader);
        if (faculty.getPersonType() != People.PersonType.FACULTY) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Faculty allowed");
        }

        boolean facultyAssociated = courseAsgnRepo.existsByFaculty_PersonIDAndCourse_CourseId(faculty.getPersonID(), courseId);
        if (!facultyAssociated) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Faculty is not associated with specified course");
        }

        String response = homeworkAssignmentService.deleteAssignment(courseId, asgnId);
        
        return ResponseEntity.ok(response); //Placeholder for further implementation
    }
}