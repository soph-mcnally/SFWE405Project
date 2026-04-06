package SFWE405.Project.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.JoinColumn;

import java.time.LocalDate;

import jakarta.persistence.Entity;  //Spring Entity

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@SuppressWarnings("unused") //gets rid of unused import warnings
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
    private University university;

    @NotNull(message = "Start date cannot be null")
    private LocalDate StartDate;

    private LocalDate EndDate;

    private Long CreditsEarned;

    private double GPA;

    private boolean TranscriptReceived; //graduation status

}