package SFWE405.Project.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.entity.AuthToken;
import SFWE405.Project.entity.People;
import SFWE405.Project.repository.AccountCredentialsRepository;
import SFWE405.Project.repository.AuthTokenRepository;

/**
 * Service class responsible for user authentication and token validation.
 *
 * This service handles login requests by validating username and password,
 * checking account status, and generating authentication tokens for
 * successful logins.
 *
 * It also validates bearer tokens for protected API requests.
 */
@Service
public class AuthenticationService {

    private final AccountCredentialsRepository accountCredentialsRepository;
    private final AuthTokenRepository authTokenRepository;

    public AuthenticationService(AccountCredentialsRepository accountCredentialsRepository,
                                 AuthTokenRepository authTokenRepository) {
        this.accountCredentialsRepository = accountCredentialsRepository;
        this.authTokenRepository = authTokenRepository;
    }

    public AuthToken login(String username, String password) {
        AccountCredentials credentials = accountCredentialsRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (credentials.getAccountStatus() != AccountCredentials.AccountStatus.ACTIVE) {
            throw new RuntimeException("Account is locked or disabled");
        }

        if (!credentials.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }

        AuthToken authToken = new AuthToken();
        authToken.setToken(UUID.randomUUID().toString());
        authToken.setPerson(credentials.getPerson());
        authToken.setCreatedAt(LocalDateTime.now());
        authToken.setExpiresAt(LocalDateTime.now().plusHours(4));
        authToken.setActive(true);

        return authTokenRepository.save(authToken);
    }

    public People validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String tokenValue = authHeader.substring(7);

        AuthToken authToken = authTokenRepository.findByTokenAndActiveTrue(tokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (authToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        return authToken.getPerson();
    }
}
