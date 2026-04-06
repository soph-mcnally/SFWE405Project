package SFWE405.Project.entity;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityId;

    private String name;
    private String location;

    @OneToMany (mappedBy = "enrolledAt") // People owns this relationship -> mappedBy is needed
    @JsonIgnore // prevents infinite recursion when serializing to JSON
    private Set<People> enrolled;

    //constructors
    public University() {}

    public University(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // helpers
    public void enrollPerson(People p) {
        p.setEnrolledAt(this);
    }
}
