/**
 * @author Brandon Sisco
 *
 * Represents the relationship between a student (People) and a course.
 *
 * This entity stores enrollment-specific information such as status,
 * grade, and the date the student enrolled in the course.
 *
 * It is used as the core data structure for building a student's
 * academic record.
 */
package SFWE405.Project.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private People person;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    private String grade;

    private LocalDate enrolledDate;

    public enum EnrollmentStatus {
        ENROLLED,
        COMPLETED,
        DROPPED,
        WITHDRAWN
    }
}

