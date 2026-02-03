package SFWE405.Project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Semesters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int year;
    @Enumerated(EnumType.STRING)
    private Season season;

}
