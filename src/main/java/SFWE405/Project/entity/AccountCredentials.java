/**
 * @author Brandon Sisco
 *
 * Represents the login credentials associated with a user (People).
 *
 * This entity stores authentication-related data such as username,
 * password, and account status. It is kept separate from the People
 * entity to maintain a clear separation between user identity and
 * authentication data.
 *
 * It is used by the authentication system to validate user login.
 */
package SFWE405.Project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class AccountCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialsId;

    @OneToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private People person;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    public enum AccountStatus {
        ACTIVE,
        LOCKED,
        DISABLED
    }
}
