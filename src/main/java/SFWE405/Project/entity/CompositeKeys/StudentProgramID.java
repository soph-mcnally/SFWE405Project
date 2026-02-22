package SFWE405.Project.entity.CompositeKeys;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgramID implements Serializable {

    private Long personID;
    private Long programID;
}
