/*
 * HomeworkAssignmentService.java
 *Last Update: 2026-04-07
 * 
 * Primary Author: @TravisPotter
 * Secondary Author: @N/A
 * 
 * Service class for handling homework assignment related business logic.
 * 
 * TODO: Implement methods for teachers to create/update/delete homework assignments for their courses.
 * TODO: Implement method to view homework file if relatedFileName is not null
 */

package SFWE405.Project.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import SFWE405.Project.entity.HomeworkAssignment;
import SFWE405.Project.repository.EnrollmentRepository;
import SFWE405.Project.repository.HomeworkAssignmentRepository;


@Service
public class HomeworkAssignmentService {
    private final HomeworkAssignmentRepository hwAsgnRepo;
    private final EnrollmentRepository enrollmentRepo;

    
    public HomeworkAssignmentService(HomeworkAssignmentRepository hwAsgnRepo, EnrollmentRepository enrollmentRepo) {
        this.hwAsgnRepo = hwAsgnRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    //Method to get HW assignments relative to a course
    public List<HomeworkAssignment> getHomeworkForCourseIfEnrolled(Long personId, Long courseId) {
        //Student --> Course Enrolled --> Course has HW assignments

        boolean enrolled = enrollmentRepo.existsByPersonPersonIDAndCourseCourseId(personId, courseId);

        if (!enrolled) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Student is not enrolled in the specified course");
        }

        return hwAsgnRepo.findByCourse_CourseId(courseId);
    }

}