package SFWE405.Project.controller;

import SFWE405.Project.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import SFWE405.Project.entity.AuthToken;
import SFWE405.Project.service.AuthenticationService;

/**
 * REST controller for handling user login requests.
 *
 * This controller accepts login credentials, passes them to the
 * authentication service, and returns an authentication token
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
    public ResponseEntity<AuthToken> login(@RequestBody LoginRequest request) {
        AuthToken authToken = authenticationService.login(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok(authToken);
    }
}
