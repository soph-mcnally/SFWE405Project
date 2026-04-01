package SFWE405.Project.entity;

import SFWE405.Project.entity.CompositeKeys.StudentProgramID;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentPrograms {
    @EmbeddedId
    private StudentProgramID studentProgramID;

    @ManyToOne
    @MapsId("personID")
    @JoinColumn(name = "personID")
    private People person;

    @ManyToOne
    @MapsId("programID")
    @JoinColumn(name = "programID")
    private Programs program;
}
