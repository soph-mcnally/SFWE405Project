package SFWE405.Project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    @JsonIgnore  // add this to prevent infinite recursion during JSON serialization - @TravisPotter
    private List<Course> courses;

    // helper method to update both sides of courses relationship
    public void addCourse(Course course) {
        courses.add(course);
    }
}
