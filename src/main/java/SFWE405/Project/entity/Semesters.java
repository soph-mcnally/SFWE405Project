package SFWE405.Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Semesters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int semesterYear;

    @Enumerated(EnumType.STRING)
    private Season season;

    public enum Season {
        SPRING,
        SUMMER,
        FALL,
        WINTER
    }

}
