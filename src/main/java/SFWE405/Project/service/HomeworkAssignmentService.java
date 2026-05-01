/*
 * HomeworkAssignmentService.java
 *Last Update: 2026-04-07
 * 
 * Primary Author: @TravisPotter
 * Secondary Author: @Jeriah Garcia
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
import SFWE405.Project.entity.Course;
import SFWE405.Project.repository.EnrollmentRepository;
import SFWE405.Project.repository.HomeworkAssignmentRepository;
import SFWE405.Project.repository.CourseAssignmentRepository;
import SFWE405.Project.repository.CourseRepository;
import SFWE405.Project.entity.Enrollment;


@Service
public class HomeworkAssignmentService {
    private final HomeworkAssignmentRepository hwAsgnRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final CourseAssignmentRepository courseAsgnRepo;
    private final CourseRepository courseRepo;

    
    public HomeworkAssignmentService(HomeworkAssignmentRepository hwAsgnRepo, EnrollmentRepository enrollmentRepo, CourseAssignmentRepository courseAsgnRepo, CourseRepository courseRepo) {
        this.hwAsgnRepo = hwAsgnRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.courseAsgnRepo = courseAsgnRepo;
        this.courseRepo = courseRepo;
    }

    //Student - View HW by course
    public List<HomeworkAssignment> getHomeworkForCourseIfEnrolled(Long personId, Long courseId) {
        //Student --> Course Enrolled --> Course has HW assignments

        boolean enrolled = enrollmentRepo.findByPersonPersonIDAndCourseCourseId(personId, courseId)
                .filter(enrollment -> enrollment.getStatus() == Enrollment.EnrollmentStatus.ENROLLED)
                .isPresent();

        if (!enrolled) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Student is not enrolled in the specified course");
        }

        return hwAsgnRepo.findByCourse_CourseId(courseId);
    }

    public List<HomeworkAssignment> getHomeworkForEnrolledCourses(Long personId) {
        return enrollmentRepo.findByPersonPersonID(personId)
                .stream()
                .filter(enrollment -> enrollment.getStatus() == Enrollment.EnrollmentStatus.ENROLLED)
                .flatMap(enrollment ->
                        hwAsgnRepo.findByCourse_CourseId(
                                enrollment.getCourse().getCourseId()
                        ).stream()
                )
                .toList();
    }

    //Faculty - View HW by course 
    public List<HomeworkAssignment> getAllHomeworkForCourseifTeacherAssociated(Long personId, Long courseId) {
        //Faculty --> Course Associated --> Course has HW assignments

        boolean facultyAssociated = courseAsgnRepo.existsByFaculty_PersonIDAndCourse_CourseId(personId, courseId);
        
        if (!facultyAssociated) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Faculty is not associated with specified course");
        }

        return hwAsgnRepo.findByCourse_CourseId(courseId);
    }

    //Faculty - Create HW for course
    public HomeworkAssignment createAssignmentForCourse(Long courseId, HomeworkAssignment newAssignment){
        //Faculty --> Course Associated --> Link Assignment to Course
        
        Course associatedCourse = courseRepo.findById(courseId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        newAssignment.setCourse(associatedCourse);

        return hwAsgnRepo.save(newAssignment);
    }

    public String deleteAssignment(Long courseId, Long asgnId){
        //Faculty --> Course --> Assignments --> Deletion
        HomeworkAssignment asgnToDelete =  hwAsgnRepo.findByCourse_CourseIdAndHomeworkAssignmentsID(courseId, asgnId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        String name = asgnToDelete.getAssignmentName();
        
        hwAsgnRepo.delete(asgnToDelete);

        return ("Deleted <" + name + "> from Course: " + courseId);
    }

    //Faculty - 
}