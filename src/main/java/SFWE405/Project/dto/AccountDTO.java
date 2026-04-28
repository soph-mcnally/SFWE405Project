/*  @Author: Karri Fox
    Data access object to go between the AccountCredentials entity and the 
    LoginController to return a person's account information.
*/

package SFWE405.Project.dto;

import java.time.LocalDateTime;

import SFWE405.Project.entity.AccountCredentials.AccountStatus;

public class AccountDTO {

    private Long credentialsId;
    private String userName;
    private String password; // only used for updates, not included in GET responses
    private String email;
    private AccountStatus accountStatus;
    private LocalDateTime dateCreated;

    // No password here for GET responses!

    public AccountDTO() {}

    // ************************
    // Getters & Setters
    // ************************
    public Long getCredentialsId() { return credentialsId; }
    public void setCredentialsId(Long credentialsId) { this.credentialsId = credentialsId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public AccountStatus getAccountStatus() { return accountStatus; }
    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // For updates, we can include the password, but it should be optional and not included in GET responses
    public void setPasswordForUpdate(String password) { this.password = password; }
    public String getPasswordForUpdate() { return password; }

}
