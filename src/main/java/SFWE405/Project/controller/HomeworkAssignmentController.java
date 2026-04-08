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
 * TODO: Allow teachers to create/update/delete homework assignments for their courses.
 */

package SFWE405.Project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import SFWE405.Project.entity.HomeworkAssignment;
import SFWE405.Project.entity.People;
import SFWE405.Project.service.AuthenticationService;
import SFWE405.Project.service.HomeworkAssignmentService;



@RestController
@RequestMapping("/api/homework-assignment")
public class HomeworkAssignmentController {
    
    private final SFWE405.Project.repository.HomeworkAssignmentRepository homeworkAssignmentRepository;
    private final AuthenticationService authenticationService;
    private final HomeworkAssignmentService homeworkAssignmentService;

    // Constructor injection for services
    public HomeworkAssignmentController(AuthenticationService authenticationService, HomeworkAssignmentService homeworkAssignmentService, SFWE405.Project.repository.HomeworkAssignmentRepository homeworkAssignmentRepository) {
        this.authenticationService = authenticationService;
        this.homeworkAssignmentService = homeworkAssignmentService;
        this.homeworkAssignmentRepository = homeworkAssignmentRepository;
    }

    //Endpoint to get HW assignments realtive to a course, Actor - student
    @GetMapping("/ViewHWAssignmentByCourse/{courseId}")
    public ResponseEntity<List<HomeworkAssignment>>  getHomeworkAssignments(
        @RequestHeader("Authorization") String authHeader,                          //Get auth token
        @PathVariable Long courseId)
    {                                              //Get courseId                 

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

    //Endpoints for teachers to CRUD homework assignments for their courses
    @GetMapping("/teacherViewAssignmentByCourse/{courseId}")
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

    /*TODO: Endpoints
     *Teacher create HW - POST
     *Teacher update HW (due date, description, etc) - PUT
     *Teacher delete HW - DELETE
     */
}