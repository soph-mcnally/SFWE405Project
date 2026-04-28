/*  @Author: Karri Fox
    Data access object to go between the Person entity and the PeopleController to 
    return a person's information. This is separate from the PersonUpdateDTO to allow 
    for more control over what fields can be returned.
*/

package SFWE405.Project.dto;

import SFWE405.Project.entity.People.DegreeLevel;
import SFWE405.Project.entity.People.PersonType;

public class PersonDTO {

    private Long personID;
    private String firstName;
    private String lastName;
    private PersonType personType;
    private DegreeLevel degreeLevel;

    // ************************
    // Optional: flatten account info
    // ************************
    private String userName;
    private String accountStatus;

    public PersonDTO() {}

    // ************************
    // Getters & Setters
    // ************************
    public Long getPersonID() { return personID; }
    public void setPersonID(Long personID) { this.personID = personID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public PersonType getPersonType() { return personType; }
    public void setPersonType(PersonType personType) { this.personType = personType; }

    public DegreeLevel getDegreeLevel() { return degreeLevel; }
    public void setDegreeLevel(DegreeLevel degreeLevel) { this.degreeLevel = degreeLevel; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getAccountStatus() { return accountStatus; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
}