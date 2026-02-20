package SFWE405.Project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Semesters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long semesterID;

    private int semesterYear;

    @Enumerated(EnumType.STRING)
    private Season season;

    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    }

    // semesters to courses relationship
    @OneToMany(mappedBy = "semester")
    private List<Courses> courses;
}
