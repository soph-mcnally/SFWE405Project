package SFWE405.Project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personID;

    private String firstName;
    private String lastName;
    
    @ManyToOne (optional = false) //this side owns this relationship, as it's the side with the multiplicity
    @JoinColumn (name = "university_id", nullable = false)
    @JsonIgnore
    private University enrolledAt;

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

    @OneToOne
    @JoinColumn(name = "credentialsId") // FK column
    @JsonIgnore // prevents infinite recursion during JSON serialization
    public AccountCredentials accountCredentials; 

    @jakarta.persistence.Transient
    private Long universityId; // Transient field to hold the university ID for input purposes (not persisted in DB)

    //****************************************************Constructors****************************************************
    public People() {}

    public People(String firstName, String lastName, PersonType personType, DegreeLevel degreeLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personType = personType;
        this.degreeLevel = degreeLevel;
    }

    //****************************************************Functions****************************************************
    // helper to maintain both sides in the database
    public void setEnrolledAt(University newUni) {
        this.enrolledAt = newUni;
    }

    @OneToMany(mappedBy = "person")
    private List<StudentPrograms> studentPrograms;

    public void setAccountCredentials(AccountCredentials savedAccount) {
        this.accountCredentials = savedAccount;
    }   
    
    public Long getUniversityId() {
        return universityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public DegreeLevel getDegreeLevel() {
        return degreeLevel;
    }
}
