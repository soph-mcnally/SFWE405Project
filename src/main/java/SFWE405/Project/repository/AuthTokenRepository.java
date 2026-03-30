package SFWE405.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.AuthToken;

/**
 * Repository interface for accessing AuthToken data.
 *
 * This repository provides database operations for authentication
 * tokens used to manage logged-in sessions.
 *
 * It is used to validate bearer tokens for protected API requests.
 */
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {

    Optional<AuthToken> findByTokenAndActiveTrue(String token);
}
