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
 * 
 */

package SFWE405.Project.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Entity
public class AccountCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long credentialsId;  // Primary key, referenced in People.java as "credentialsId"

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // stored as hashed value, not plain text

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private LocalDateTime dateCreated;

    @OneToOne(mappedBy = "accountCredentials")  // Bidirectional relationship with People
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private People person;

    // Enum for account status (add more values as needed)
    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }

    // Constructors
    public AccountCredentials() {}

    public AccountCredentials(String userName, String password, AccountStatus accountStatus, LocalDateTime dateCreated) {
        this.userName = userName;
        this.password = password;
        this.accountStatus = accountStatus;
        this.dateCreated = dateCreated;
    }

    // Helper method to maintain bidirectional relationship (mirrors People.setAccount)
    public void setPerson(People person) {
        this.person = person;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public People getPerson() {
        return person;
    }

    public Long getCredentialsId() {
        return credentialsId;
    }
}
