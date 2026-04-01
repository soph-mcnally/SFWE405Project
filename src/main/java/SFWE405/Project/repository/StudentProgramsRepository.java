
package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.CompositeKeys.StudentProgramID;
import SFWE405.Project.entity.StudentPrograms;

public interface StudentProgramsRepository extends JpaRepository<StudentPrograms, StudentProgramID> {}