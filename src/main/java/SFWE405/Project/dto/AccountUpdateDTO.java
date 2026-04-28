/*  @Author: Karri Fox
    Data access object to go between the Person and AccountCredentials entities and 
    the ManageUserAccountController to update a person's profile information and 
    account credentials. This is separate from the PersonDTO and AccountDTO
*/
package SFWE405.Project.dto;

import SFWE405.Project.entity.AccountCredentials.AccountStatus;

public class AccountUpdateDTO {

    private String userName;
    private String email;
    private String password;
    private AccountStatus accountStatus;

    // ************************
    // Getters & Setters
    // ************************
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public AccountStatus getAccountStatus() { return accountStatus; }
    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }
}
