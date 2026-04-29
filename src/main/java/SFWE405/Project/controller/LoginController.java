package SFWE405.Project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.Project.dto.LoginRequest;
import SFWE405.Project.dto.LoginResponse;
import SFWE405.Project.entity.AuthToken;
import SFWE405.Project.service.AuthenticationService;

/**
 * REST controller for handling user login requests.
 *
 * This controller accepts login credentials, passes them to the
 * authentication service, and returns authentication token data
 * when login is successful.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        AuthToken authToken = authenticationService.login(
                request.getUsernameOrEmail(),
                request.getPassword()
        );

        LoginResponse response = new LoginResponse();
        response.setToken(authToken.getToken());
        response.setCreatedAt(authToken.getCreatedAt());
        response.setExpiresAt(authToken.getExpiresAt());
        response.setPersonId(authToken.getPerson().getPersonID());
        response.setCredentialsId(authToken.getPerson().getAccountCredentials().getCredentialsId());
        response.setRole(authToken.getPerson().getPersonType().name());
        response.setEmail(authToken.getPerson().getAccountCredentials().getEmail());

        return ResponseEntity.ok(response);
    }

    // endpoint for handling logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        try {
            authenticationService.logout(authHeader);
            return ResponseEntity.ok("Logged out successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
