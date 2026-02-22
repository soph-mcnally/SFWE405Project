package SFWE405.Project.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Table;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import jakarta.persistence.JoinColumn;

import java.time.LocalDate;

import lombok.Data;                 //Lombok getters & setters
import jakarta.persistence.Entity;  //Spring Entity


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