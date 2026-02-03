package SFWE405.Project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // how to add university id foreign key?
    // and semester id?
    private int courseCode;
    private String courseName;

    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    private double unitsAmount;
    private boolean upperDivision;

    public enum CourseType {
        LECTURE,
        LAB,
        DISCUSSION,
    }



}
