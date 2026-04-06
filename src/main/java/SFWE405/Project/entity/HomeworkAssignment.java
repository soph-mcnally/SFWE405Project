package SFWE405.Project.entity;


import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull; 
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@SuppressWarnings("unused") //gets rid of unused import warnings
@Entity
public class HomeworkAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long homeworkAssignmentsID;

    @NotNull(message = "Assignment name cannot be null")
    private String assignmentName;

    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;
    
    private String relatedFileName = null;  //allows for accessing files possibly needed (Likely stored in templates)

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false) //FK column in join table references the Courses entity
    private Course course;
}
