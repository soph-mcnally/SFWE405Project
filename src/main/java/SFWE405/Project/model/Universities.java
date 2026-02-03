package SFWE405.Project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Universities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String location;
}
