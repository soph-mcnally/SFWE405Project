/*
 * HomeworkAssignmentService.java
 *Last Update: 2026-04-07
 * 
 * Primary Author: @TravisPotter
 * Secondary Author: @N/A
 * 
 * Service class for handling homework assignment related business logic.
 * Provides functionality for students to view homework assignments for their courses and for teachers to manage homework assignments for their courses.
 *  -- Student: Can view homework assignments for courses they are enrolled in.
 *  -- Faculty: Can create, update, and delete homework assignments for courses they are associated with.
 * 
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
import SFWE405.Project.repository.CourseAssignmentRepository;


@Service
public class HomeworkAssignmentService {
    private final HomeworkAssignmentRepository hwAsgnRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final CourseAssignmentRepository courseAsgnRepo;

    
    public HomeworkAssignmentService(HomeworkAssignmentRepository hwAsgnRepo, EnrollmentRepository enrollmentRepo, CourseAssignmentRepository courseAsgnRepo) {
        this.hwAsgnRepo = hwAsgnRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.courseAsgnRepo = courseAsgnRepo;
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

    public List<HomeworkAssignment> getAllHomeworkForCourseifTeacherAssociated(Long personId, Long courseId) {
        //Faculty --> Course Associated --> Course has HW assignments

        boolean teacherAssociated = courseAsgnRepo.existsByFaculty_PersonIDAndCourse_CourseId(personId, courseId);
        
        if (!teacherAssociated) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Faculty is not associated with specified course");
        }

        return hwAsgnRepo.findByCourse_CourseId(courseId);
    }

}