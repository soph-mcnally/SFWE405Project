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
 * @author Brandon Sisco
 *
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

    public AuthToken login(String usernameOrEmail, String password) {
      //  AccountCredentials credentials = accountCredentialsRepository.findByUserName(usernameOrEmail)
      //          .orElseThrow(() -> new RuntimeException("Invalid user name or password"));

        // student login use case has username or email, so adding that functionality -- JA
        AccountCredentials credentials;
        if (usernameOrEmail.contains("@")) {
            credentials = accountCredentialsRepository.findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new RuntimeException("Invalid username/email or password"));
        }
        else {
            credentials = accountCredentialsRepository.findByUserName(usernameOrEmail)
                    .orElseThrow(() -> new RuntimeException("Invalid username/email or password"));
        }

        if (credentials.getAccountStatus() != AccountCredentials.AccountStatus.ACTIVE) {
            throw new RuntimeException("Account is locked or disabled");
        }

        if (!credentials.getPassword().equals(password)) {
            throw new RuntimeException("Invalid user name or password");
        }

        AuthToken authToken = new AuthToken();
        authToken.setToken(UUID.randomUUID().toString());
        authToken.setPerson(credentials.getPerson());
        authToken.setCreatedAt(LocalDateTime.now());
        authToken.setExpiresAt(LocalDateTime.now().plusMinutes(30)); // tokens now expire after 30 minutes
        authToken.setActive(true);

        return authTokenRepository.save(authToken);
    }

    public People validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String tokenValue = authHeader.substring(7); // index position after "Bearer "

        AuthToken authToken = authTokenRepository.findByTokenAndActiveTrue(tokenValue) // token exists and is active
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (authToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        }

        return authToken.getPerson();
    }
}
