/*
 * @author Karri Fox
 *
 * ProgramsRepository interface for accessing the Programs table in the database, allowing us to perform CRUD operations on program data.
 * 
 */

package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.Programs;

public interface ProgramsRepository extends JpaRepository<Programs, Long> {}