/**
 * @author Brandon Sisco
 *
 * Represents an authentication token for a logged-in user.
 *
 * This entity stores bearer token information used to maintain an
 * authenticated session, including the associated user, creation time,
 * expiration time, and whether the token is active.
 *
 * It is used to authorize requests to protected endpoints in the system.
 */
package SFWE405.Project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class
AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(nullable = false, unique = true, length = 200)
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private People person;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;
}