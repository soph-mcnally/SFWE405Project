/*

Created By: Gavin Hernandez

Entity for University Requirements

 */

package SFWE405.Project.entity;

import jakarta.persistence.*;
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
    @JoinColumn(name = "universityId") // creates the FK column (points to universities)
    private University university;

    private Long category;

    public UniversityRequirements(String requirementDescription, University university, Long category) {
        this.requirementDescription = requirementDescription;
        this.university = university;
        this.category = category;
    }
}