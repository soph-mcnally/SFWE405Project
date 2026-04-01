package SFWE405;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import SFWE405.Project.entity.People;
import SFWE405.Project.entity.Programs;
import SFWE405.Project.entity.StudentPrograms;
import SFWE405.Project.entity.University;
import SFWE405.Project.entity.CompositeKeys.StudentProgramID;
import SFWE405.Project.repository.PeopleRepository;
import SFWE405.Project.repository.ProgramsRepository;
import SFWE405.Project.repository.StudentProgramsRepository;
import SFWE405.Project.repository.UniversityRepository;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentProgramsRepositoryTest {

    @Autowired
    private StudentProgramsRepository studentProgramsRepository;

    @Autowired
    private ProgramsRepository programsRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private UniversityRepository universitiesRepository;

    private University uOfA;

    @BeforeAll
    void setUp(){
        uOfA = new University("U of A", "Tucson, Arizona");
        universitiesRepository.save(uOfA);
    }

    @Test
    void shouldSaveStudentProgram() {

        // ---------- Arrange ----------

        Programs program = new Programs();
        program.setProgramName("Computer Science");
        program.setCreditsRequired(120);
        program = programsRepository.save(program);

        People person = new People();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.doe@test.com");
        person.setEnrolledAt(uOfA);
        person = peopleRepository.save(person);

        StudentProgramID id = new StudentProgramID(
                person.getPersonID(),
                program.getProgramID()
        );

        StudentPrograms studentProgram = new StudentPrograms();
        studentProgram.setStudentProgramID(id);
        studentProgram.setPerson(person);
        studentProgram.setProgram(program);

        // ---------- Act ----------

        StudentPrograms saved = studentProgramsRepository.save(studentProgram);

        // ---------- Assert ----------

        assertNotNull(saved);
        assertEquals(person.getPersonID(), saved.getStudentProgramID().getPersonID());
        assertEquals(program.getProgramID(), saved.getStudentProgramID().getProgramID());
    }

    @Test
    void shouldFindStudentProgramById() {

        // Arrange
        Programs program = programsRepository.save(
                new Programs(null, "Engineering", 130, null)
        );

        People person = new People("Jane", "Smith", "jane@test.com",
                        People.PersonType.STUDENT,
                        People.DegreeLevel.UNDERGRADUATE);
        person.setEnrolledAt(uOfA);
        peopleRepository.save(person);
        

        StudentProgramID id = new StudentProgramID(
                person.getPersonID(),
                program.getProgramID()
        );

        StudentPrograms sp = new StudentPrograms(id, person, program);
        studentProgramsRepository.save(sp);

        // Act
        Optional<StudentPrograms> found =
                studentProgramsRepository.findById(id);

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Engineering",
                found.get().getProgram().getProgramName());
    }

    @Test
    void shouldDeleteStudentProgram() {

        Programs program = programsRepository.save(
                new Programs(null, "Biology", 100, null)
        );

        People person = new People("Alice", "Brown", "alice@test.com",
                        People.PersonType.STUDENT,
                        People.DegreeLevel.GRADUATE);
        person.setEnrolledAt(uOfA);
        peopleRepository.save(person);

        StudentProgramID id = new StudentProgramID(
                person.getPersonID(),
                program.getProgramID()
        );

        StudentPrograms sp = new StudentPrograms(id, person, program);
        studentProgramsRepository.save(sp);

        // Act
        studentProgramsRepository.deleteById(id);

        // Assert
        assertFalse(studentProgramsRepository.findById(id).isPresent());
    }
}
