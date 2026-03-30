package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import SFWE405.Project.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
