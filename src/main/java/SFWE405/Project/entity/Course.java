package SFWE405.Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import lombok.Data;

@Data
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
}
