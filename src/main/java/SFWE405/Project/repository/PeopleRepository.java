package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.People;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<People, Long> {}