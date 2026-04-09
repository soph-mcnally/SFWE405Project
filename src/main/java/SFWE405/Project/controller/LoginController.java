package SFWE405.Project.controller;

import SFWE405.Project.dto.LoginRequest;
import SFWE405.Project.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        AuthToken authToken = authenticationService.login(
                // request.getUserName(),
                request.getUsernameOrEmail(),
                request.getPassword()
        );

        LoginResponse response = new LoginResponse();
        response.setToken(authToken.getToken());
        response.setCreatedAt(authToken.getCreatedAt());
        response.setExpiresAt(authToken.getExpiresAt());
        response.setPersonId(authToken.getPerson().getPersonID());
        response.setRole(authToken.getPerson().getPersonType().name());

        return ResponseEntity.ok(response);
    }
}
