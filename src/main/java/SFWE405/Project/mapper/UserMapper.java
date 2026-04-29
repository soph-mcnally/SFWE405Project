/*  @Author: Karri Fox
    Mapper class to convert between the Person and AccountCredentials entities 
    and the various DTOs used in the PeopleController, LoginController, and 
    ManageUserAccountController. This is separate from the controllers to allow 
    for more control over what fields are included in the DTOs and to keep the 
    controllers cleaner.
*/
package SFWE405.Project.mapper;

import SFWE405.Project.dto.AccountDTO;
import SFWE405.Project.dto.PersonDTO;
import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.entity.People;

public class UserMapper {

    // ************************
    // ACCOUNT
    // ************************
    public static AccountDTO toAccountDTO(AccountCredentials entity) {
        AccountDTO dto = new AccountDTO();
        dto.setCredentialsId(entity.getCredentialsId());
        dto.setUserName(entity.getUserName());
        dto.setEmail(entity.getEmail());
        dto.setAccountStatus(entity.getAccountStatus());
        dto.setDateCreated(entity.getDateCreated());
        return dto;
    }

    // ************************
    // PERSON
    // ************************
    public static PersonDTO toPersonDTO(People entity) {
        PersonDTO dto = new PersonDTO();

        dto.setPersonID(entity.getPersonID());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPersonType(entity.getPersonType());
        dto.setDegreeLevel(entity.getDegreeLevel());

        if (entity.getAccountCredentials() != null) {
            dto.setUserName(entity.getAccountCredentials().getUserName());
            dto.setAccountStatus(entity.getAccountCredentials().getAccountStatus().name());
        }

        return dto;
    }
}
