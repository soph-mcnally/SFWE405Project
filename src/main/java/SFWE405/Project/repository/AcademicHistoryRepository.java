package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import SFWE405.Project.entity.AcademicHistory;

public interface AcademicHistoryRepository extends JpaRepository<AcademicHistory, Long> {
}