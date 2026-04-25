package SFWE405.Project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    Optional<Enrollment> findByPersonPersonIDAndCourseCourseId(Long personId, Long courseId);

    boolean existsByPersonPersonIDAndCourseCourseId(Long personId, Long courseId); //Enrollment check for getting HW relative to a course - @TravisPotter
    @Query("""
        SELECT e
        FROM Enrollment e
        JOIN e.course c
        WHERE e.person.personID = :personId
        AND (
            LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.courseName) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Enrollment> findAcademicRecordByPersonAndSearch(
            @Param("personId") Long personId,
            @Param("search") String search,
            Pageable pageable
    );
}
