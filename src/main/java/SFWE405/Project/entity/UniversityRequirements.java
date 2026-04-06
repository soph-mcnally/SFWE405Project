package SFWE405.Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor //generates empty constructor
@Entity

public class UniversityRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requirementID; // PK

    private String requirementDescription;

    @ManyToOne // universities is owner of relationship
    @JoinColumn(name = "universitiesID") // creates the FK column (points to universities)
    private University university;

    public UniversityRequirements(String requirementDescription, University university) {
        this.requirementDescription = requirementDescription;
        this.university = university;
    }
}