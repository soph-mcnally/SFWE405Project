package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.University;

public interface UniversitiesRepository extends JpaRepository<University, Long> {}