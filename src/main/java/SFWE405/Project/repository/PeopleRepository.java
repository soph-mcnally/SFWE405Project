package SFWE405.Project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.People;


public interface PeopleRepository extends JpaRepository<People, Long> {}