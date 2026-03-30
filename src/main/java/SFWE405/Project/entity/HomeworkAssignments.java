package SFWE405.Project.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*; //validating attributes
import java.time.LocalDate;

import lombok.Data;                 //Lombok getters & setters

@SuppressWarnings("unused") //gets rid of unused import warnings
@Data
@Entity
public class HomeworkAssignments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Assignment name cannot be null")
    private String assignmentName;

    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;
    
    private String relatedFileName = null;  //allows for accessing files possibly needed (Likely stored in templates)

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) //FK column in join table references the Courses entity
    private Courses course;
}
