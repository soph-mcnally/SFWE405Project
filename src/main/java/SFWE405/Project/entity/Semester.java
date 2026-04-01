package SFWE405.Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;

import lombok.Data;

import java.util.List;

@Data
@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long semesterId;

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
    private List<Course> courses;

    // helper method to update both sides of courses relationship
    public void addCourse(Course course) {
        courses.add(course);
        course.setSemester(this);
    }
}
