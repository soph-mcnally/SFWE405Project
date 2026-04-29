package SFWE405.Project.repository;
import SFWE405.Project.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * @author Sophie McNally
 *
 * Repository interface for Semester entity
 */
@Repository
public interface SemestersRepository extends JpaRepository<Semester, Long> {
}
