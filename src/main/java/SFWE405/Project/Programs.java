package SFWE405.Project;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Programs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String programName;
    private double creditsRequired;
}
