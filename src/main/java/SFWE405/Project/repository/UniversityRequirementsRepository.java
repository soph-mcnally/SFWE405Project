package SFWE405.Project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import SFWE405.Project.entity.UniversityRequirements;
import SFWE405.Project.entity.University;

public interface UniversityRequirementsRepository extends JpaRepository<UniversityRequirements, Long> {
    List<UniversityRequirements> findByUniversity(University university);
}