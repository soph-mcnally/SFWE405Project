package SFWE405.Project.entity;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Programs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programID;

    private String programName;
    private Integer creditsRequired;


    @OneToMany(mappedBy = "program")
    private List<StudentPrograms> studentPrograms;
}
