package SFWE405.Project.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Universities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToMany (mappedBy = "enrolledAt") // People owns this relationship -> mappedBy is needed
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<People> enrolled = new HashSet<>();

    //constructors
    public Universities() {}

    public Universities(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // helpers
    public void enrollPerson(People p) {
        p.setEnrolledAt(this);
    }
}
