package SFWE405.Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import java.util.List;
import lombok.Setter;
import lombok.Getter;

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
    @JsonIgnore
    private List<Course> courses;

    // helper method to update both sides of courses relationship
    public void addCourse(Course course) {
        courses.add(course);
    }
}
