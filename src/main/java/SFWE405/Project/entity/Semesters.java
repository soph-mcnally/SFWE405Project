package SFWE405.Project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    // helper method to update both sides of courses relationship
    public void addCourse(Courses course) {
        courses.add(course);
        course.setSemester(this);
    }
}
