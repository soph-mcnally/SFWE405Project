package SFWE405.Project.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import java.util.HashSet;

@Data
@Entity
public class Universities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String location;

    @OneToMany (mappedBy = "enrolledAt"); // Peopl owns this relationship -> mappedBy is needed
    private Set<People> enrolled = new HashSet<>();

    //constructors
    public Universities() {}

    public Universities(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Set<People> getEnrolled() { return enrolled; }

    // helpers
    public void enrollPerson(People p) {
        enrolled.add(p);
    }
}
