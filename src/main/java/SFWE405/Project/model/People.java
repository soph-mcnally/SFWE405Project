package SFWE405.Project.model;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;

    public enum PersonType {
        STUDENT,
        FACULTY,
        ADMIN
    }
    private PersonType personType;

    public enum degreeLevel {
        UNDERGRADUATE,
        GRADUATE
    }
    private degreeLevel degreeLevel;

}
