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
    private Long credentialsId;
    private String role;

    public LoginResponse(){
        // Default constructor
    }  

    public LoginResponse(String token, String email, LocalDateTime createdAt, LocalDateTime expiresAt, Long personId, Long credentialsId, String role) {
        this.token = token;
        this.email = email;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.personId = personId;
        this.credentialsId = credentialsId;
        this.role = role;
    }

    // Getters & Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getPersonId() { return personId; }
    public void setPersonId(Long personId) { this.personId = personId; }

    public Long getCredentialsId() { return credentialsId; }
    public void setCredentialsId(Long credentialsId) { this.credentialsId = credentialsId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
}
