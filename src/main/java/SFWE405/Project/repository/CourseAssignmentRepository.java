/*
 * CourseAssignmentRepository.java
 * Last Update: 2026-04-08
 * 
 * Primary Author: @TravisPotter
 * 
 * Reppository interface for CourseAssignment entity, storing faculty-course associations.
 */

package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SFWE405.Project.entity.Course;
import SFWE405.Project.entity.CourseAssignment;

import java.util.List;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, Long> {
    boolean existsByFaculty_PersonIDAndCourse_CourseId(Long personId, Long courseId);

    @Query("SELECT ca.course FROM CourseAssignment ca WHERE ca.faculty.personID = :personId")
    List<Course> findCoursesByFacultyPersonID(@Param("personId") Long personId);
    
}