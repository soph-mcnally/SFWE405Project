package SFWE405.Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.Enrollment;

/**
 * Repository interface for accessing Enrollment data.
 *
 * This repository provides database operations for the relationship
 * between students and courses, including enrollment status, grades,
 * and enrollment dates.
 *
 * It is used to retrieve a student's course history when building
 * an academic record.
 */
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByPersonPersonID(Long personId);
}
