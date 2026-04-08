/*
 * HomeworkAssignmentRepository.java
 *Last Update: 2026-04-07
 * 
 * Primary Author: @TravisPotter
 * Secondary Author: @N/A
 * 
 * Repository interface for accessing HomeworkAssignment data.
 */

package SFWE405.Project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.HomeworkAssignment;


public interface HomeworkAssignmentRepository extends JpaRepository<HomeworkAssignment, Long> {
    List<HomeworkAssignment> findByCourse_CourseId(Long courseId);
}
