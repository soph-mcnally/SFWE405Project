/*  @Author: Karri Fox
    Data access object to go between the Person and AccountCredentials entities and 
    the ManageUserAccountController to return a person's profile information 
    and account credentials. This is separate from the PersonDTO and AccountDTO to allow 
    for more control over what fields can be returned.
*/

package SFWE405.Project.dto;

import SFWE405.Project.entity.AccountCredentials.AccountStatus;
import SFWE405.Project.entity.People.DegreeLevel;
import SFWE405.Project.entity.People.PersonType;

public class UserProfileDTO {

    private Long personId;
    private String firstName;
    private String lastName;

    private String userName;
    private AccountStatus accountStatus;
    private String email;
    private PersonType personType;
    private DegreeLevel degreeLevel;

    // ************************
    // Getters & Setters
    // ************************
    public Long getPersonId() { return personId; }
    public void setPersonId(Long personId) { this.personId = personId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public AccountStatus getAccountStatus() { return accountStatus; }
    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }

    public PersonType getPersonType() { return personType; }
    public void setPersonType(PersonType personType) { this.personType = personType; }

    public DegreeLevel getDegreeLevel() { return degreeLevel; }
    public void setDegreeLevel(DegreeLevel degreeLevel) { this.degreeLevel = degreeLevel; }
}
