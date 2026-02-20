package SFWE405.Project.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CourseID")
    private Long courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UniversityID", nullable = false)
    private Universities university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SemesterID", nullable = false)
    private Semesters semester;

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
