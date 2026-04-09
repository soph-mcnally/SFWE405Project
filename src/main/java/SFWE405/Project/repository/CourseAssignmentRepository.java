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

import SFWE405.Project.entity.CourseAssignment;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, Long> {
    boolean existsByFaculty_PersonIDAndCourse_CourseId(Long personId, Long courseId);
    
}