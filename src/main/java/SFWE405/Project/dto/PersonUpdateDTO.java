/*  @Author: Karri Fox
    Data access object to go between the Person entity and the PeopleController to 
    update a person's information. This is separate from the PersonDTO to allow for 
    more control over what fields can be updated.
*/

package SFWE405.Project.dto;


import SFWE405.Project.entity.People.DegreeLevel;
import SFWE405.Project.entity.People.PersonType;

public class PersonUpdateDTO {

    private String firstName;
    private String lastName;
    private PersonType personType;
    private DegreeLevel degreeLevel;

    // ************************
    // Getters & Setters
    // ************************
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public PersonType getPersonType() { return personType; }
    public void setPersonType(PersonType personType) { this.personType = personType; }

    public DegreeLevel getDegreeLevel() { return degreeLevel; }
    public void setDegreeLevel(DegreeLevel degreeLevel) { this.degreeLevel = degreeLevel; }
}