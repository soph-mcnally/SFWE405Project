package SFWE405.Project.entity;

import SFWE405.Project.entity.Universities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data //<- is this annotation needed?
@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personID;

    private String firstName;
    private String lastName;
    
    @Column(unique = true, nullable = false) //each person should have a unique email -> no repeating emails
    private String email;

    /* adding the relationship here for a join table, but I'm still uncertain how we are doing this
    @ManyToMany
    @JoinTable(
            name = "people_courses",
            joinColumns = @JoinColumn(name = "") <- need to figure out what the FK is we are joining here
            inverseJoinColumn = @JoinColumn(name = "") <- same as above
    */
    // private Set<Courses> courses = new HashSet<>();

    @ManyToOne (optional = false) //this side owns this relationship, as it's the side with the multiplicity
    @JoinColumn (name = "university_id", nullable = false)
    @ToString.Exclude // excluding these from the @Data annotation to avoid recursion and equality bugs
    @EqualsAndHashCode.Exclude
    private Universities enrolledAt;

    public enum PersonType { // <- we should consider moving these into their own file
        STUDENT,
        FACULTY,
        ADMIN
    }
    @Enumerated(EnumType.STRING)
    private PersonType personType; 

    public enum DegreeLevel {
        UNDERGRADUATE,
        GRADUATE
    }
    @Enumerated(EnumType.STRING)
    private DegreeLevel degreeLevel;

    //constructors
    public People() {}

    public People(String firstName, String lastName, String email, PersonType personType, DegreeLevel degreeLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personType = personType;
        this.degreeLevel = degreeLevel;
    }

    // helper to maintain both sides in the database
    public void setEnrolledAt(Universities newUni) {
        if (this.enrolledAt != null) {
            this.enrolledAt.getEnrolled().remove(this);
        }
        this.enrolledAt = newUni;
        if (newUni != null) {
            newUni.getEnrolled().add(this);
        }
    }

    @OneToMany(mappedBy = "person")
    private List<StudentPrograms> studentPrograms;
        
}
