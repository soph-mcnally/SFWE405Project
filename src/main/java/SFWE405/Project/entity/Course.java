package SFWE405.Project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jeriah Garcia
 *
 * Entity class representing a university course in the system.
 *
 * This file stores course information such as course code, name, type,
 * semester, university association, unit amount, and upper division status.
 *
 * It also defines the relationship between courses, semesters, and universities
 * using JPA annotations.
 */

@Getter
@Setter
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CourseID")
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UniversityID", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SemesterID", nullable = false)
    private Semester semester;

    @Column(name = "CourseCode", nullable = false)
    private String courseCode;

    @Column(name = "CourseName", nullable = false)
    private String courseName;

    @Enumerated(EnumType.STRING)
    @Column(name = "CourseType", nullable = false)
    private CourseType courseType;

    @Column(name = "UnitsAmount", nullable = false)
    private Integer unitsAmount;

    @Column(name = "UpperDivision", nullable = false)
    private Boolean upperDivision;

    public enum CourseType {
        LECTURE,
        LAB,
        DISCUSSION
    }

    public Semester getSemester() {
        return semester;
    }
}
