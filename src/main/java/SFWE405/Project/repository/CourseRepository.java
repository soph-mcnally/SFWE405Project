package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import SFWE405.Project.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByUniversityUniversitiesID(Long universitiesID);
    List<Course> findByUniversityUniversitiesIDAndSemesterId(Long universitiesID, Long semesterId);
}
