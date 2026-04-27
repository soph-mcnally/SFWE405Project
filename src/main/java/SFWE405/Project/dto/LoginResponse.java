package SFWE405.Project.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Brandon Sisco
 *
 * DTO representing the response returned after a successful login.
 *
 * This object is used to return authentication token information
 * without exposing the full AuthToken entity or related entity graph.
 */
@Data
public class LoginResponse {
    private String token;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long personId;
    private String role;
}
