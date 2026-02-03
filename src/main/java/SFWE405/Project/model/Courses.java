package SFWE405.Project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



}
