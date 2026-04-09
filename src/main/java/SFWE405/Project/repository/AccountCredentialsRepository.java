package SFWE405.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SFWE405.Project.entity.AccountCredentials;

/**
 * Repository interface for accessing AccountCredentials data.
 *
 * This repository provides database operations for user login
 * credentials, including looking up a user by username during
 * authentication.
 *
 * It is used by the authentication service to validate login requests.
 */
public interface AccountCredentialsRepository extends JpaRepository<AccountCredentials, Long> {

    Optional<AccountCredentials> findByUserName(String userName);
   // Optional<AccountCredentials> findByEmail(String email);
}
