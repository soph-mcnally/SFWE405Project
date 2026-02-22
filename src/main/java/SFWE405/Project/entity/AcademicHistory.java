package SFWE405.Project.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@SuppressWarnings("unused") //gets rid of unused import warnings
@Data
@Entity
public class AcademicHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long academicHistoryID;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false) //FK
    private People person;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false) //FK
    private Universities university;

    @NotNull(message = "Start date cannot be null")
    private LocalDate StartDate;

    private LocalDate EndDate;

    private Long CreditsEarned;

    private double GPA;

    private boolean TranscriptReceived; //graduation status

}