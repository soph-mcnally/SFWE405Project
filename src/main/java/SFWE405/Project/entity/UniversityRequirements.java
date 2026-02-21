package SFWE405.Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

@Data
@NoArgsConstructor //generates empty constructor
@Entity

public class UniversityRequirements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requirementID; // PK

    private String requirementDescription;

    @ManyToOne // universities is owner of relationship
    @JoinColumn(name = "universitiesID") // creates the FK column (points to universities)
    private Universities university;

    public UniversityRequirements(String requirementDescription, Universities university) {
        this.requirementDescription = requirementDescription;
        this.university = university;
    }
}