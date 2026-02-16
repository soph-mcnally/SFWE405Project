package SFWE405.Project.model;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Data //<- is this annotation needed?
@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    
    @Cloumn(unique = true) //each person should have a unique email -> no repeating emails
    private String email;

    /* adding the relationship here for a join table, but I'm still uncertain how we are doing this
    @ManyToMany
    @JoinTable(
            name = "people_courses",
            joinColumns = @JoinColumn(name = "") <- need to figure out what the FK is we are joining here
            inverseJoinColumn = @JoinColumn(name = "") <- same as above
    */
    private Set<Courses> courses = new HashSet<>();

    @ManyToOne (optional = false) //this side owns this relationship, as it's the side with the multiplicity
    private Universities enrolledAt;

    public enum PersonType { // <- we should consider moving these into their own file
        STUDENT,
        FACULTY,
        ADMIN
    }
    private PersonType personType; 

    public enum DegreeLevel {
        UNDERGRADUATE,
        GRADUATE
    }
    private DegreeLevel degreeLevel;

    //constructors
    public People() {}

    public People(String firstName, String lastName, String email) {
        this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
    }

    // getters/setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String name) { firstName = name; }

    public String getLastName() { return lastName;}
    public void setLastName(String name) { lastName = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public DegreeLevel getDegreeLevel() { return degreeLevel; }
    public void setDegreeLevel(DegreeLevel degreeLevel) { this.degreeLevel = degreeLevel; } //not sure if I need to typecast the parameter...

    public PersonType getPersonType() { return personType; }
    public void setPersonType(PersonType type) { personType = type; } // same question as above

    public Universities getEnrolledAt() { return enrolledAt; }
    public void setEnrolledAt(Universities university) { // need to update both sides of the database for the relationships described
        enrolledAt = university;
        university.enrollPerson(this);
    }
        
}
