package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import SFWE405.Project.entity.HomeworkAssignment;

public interface HomeworkAssignmentsRepository extends JpaRepository<HomeworkAssignment, Long> {
}
