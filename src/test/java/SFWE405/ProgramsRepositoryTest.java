package SFWE405;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import SFWE405.Project.entity.Programs;
import SFWE405.Project.repository.ProgramsRepository;

@DataJpaTest
class ProgramsRepositoryTest {
    @Autowired
    private ProgramsRepository programsRepository;

    @Test
    void shouldSaveProgram() {

        // Arrange
        Programs programs = new Programs();
        programs.setProgramName("Computer Science");
        programs.setCreditsRequired(120);

        // Act
        Programs savedPrograms = programsRepository.save(programs);

        // Assert
        assertNotNull(savedPrograms.getProgramID());
        assertEquals("Computer Science", savedPrograms.getProgramName());
        assertEquals(120, savedPrograms.getCreditsRequired());
    }

    @Test
    void shouldFindProgramById() {

        // Arrange
        Programs programs = new Programs();
        programs.setProgramName("Mechanical Engineering");
        programs.setCreditsRequired(128);

        Programs savedPrograms = programsRepository.save(programs);

        // Act
        Optional<Programs> found = programsRepository.findById(savedPrograms.getProgramID());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Mechanical Engineering", found.get().getProgramName());
    }

    @Test
    void shouldDeleteProgram() {

        // Arrange
        Programs programs = new Programs();
        programs.setProgramName("Biology");
        programs.setCreditsRequired(100);

        Programs saved = programsRepository.save(programs);

        // Act
        programsRepository.deleteById(saved.getProgramID());

        // Assert
        Optional<Programs> deleted = programsRepository.findById(saved.getProgramID());
        assertFalse(deleted.isPresent());
    }
}