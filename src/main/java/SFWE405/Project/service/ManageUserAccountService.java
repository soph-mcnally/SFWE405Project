/**
 * @author Karri Fox
 *
 * Service to allow the user to manage their user account
 * 
 */

package SFWE405.Project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import SFWE405.Project.dto.UpdateUserProfileDTO;
import SFWE405.Project.dto.UserProfileDTO;
import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.entity.AccountCredentials.AccountStatus;
import SFWE405.Project.entity.People;
import SFWE405.Project.entity.University;
import SFWE405.Project.repository.AccountCredentialsRepository;
import SFWE405.Project.repository.PeopleRepository;
import SFWE405.Project.repository.UniversityRepository;


@Service
public class ManageUserAccountService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AccountCredentialsRepository accountRepository;
    private final PeopleRepository personRepository;
    private final UniversityRepository universityRepository;

    public ManageUserAccountService(AccountCredentialsRepository accountRepository,
                                    PeopleRepository personRepository,
                                    UniversityRepository universityRepository) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.universityRepository = universityRepository;
    }

    // Create Account
    public AccountCredentials createAccount(Long personId, AccountCredentials account) {
        People person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        account.setAccountStatus(AccountStatus.ACTIVE);

        AccountCredentials savedAccount = accountRepository.save(account);

        // optional: link account to person if relationship exists
        person.setAccountCredentials(savedAccount);
        personRepository.save(person);

        return savedAccount;
    }

    // Get Account
    public AccountCredentials getAccount(Long credentialsId) {
        return accountRepository.findById(credentialsId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Get All Accounts
    public List<AccountCredentials> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Delete Account
    public void deleteAccount(Long credentialsId) {
        accountRepository.deleteById(credentialsId);
    }

    public UserProfileDTO updateUserProfile(Long personId, UpdateUserProfileDTO dto) {
        People person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        AccountCredentials account = person.getAccountCredentials();
        if (account == null) {
            throw new RuntimeException("Account not found for this user");
        }

        // *************************
        // UPDATE PERSON
        // *************************
        if (dto.getFirstName() != null)
            person.setFirstName(dto.getFirstName());

        if (dto.getLastName() != null)
            person.setLastName(dto.getLastName());

        if (dto.getPersonType() != null)
            person.setPersonType(dto.getPersonType());

        if (dto.getDegreeLevel() != null)
            person.setDegreeLevel(dto.getDegreeLevel());

        if (dto.getUniversityId() != null) {
            University uni = universityRepository.findById(dto.getUniversityId())
                    .orElseThrow(() -> new RuntimeException("University not found"));
            person.setEnrolledAt(uni);
        }

        // *************************
        // UPDATE ACCOUNT
        // *************************
        if (dto.getUserName() != null)
            account.setUserName(dto.getUserName());

        if (dto.getEmail() != null)
            account.setEmail(dto.getEmail());

        if (dto.getAccountStatus() != null)
            account.setAccountStatus(dto.getAccountStatus());

        // 🔒 CENTRALIZED PASSWORD LOGIC
        updatePasswordIfPresent(account, dto.getPassword());

        // *************************
        // SAVE
        // *************************
        personRepository.save(person);
        accountRepository.save(account);

        return buildUserProfileDTO(person, account);
    }

    private UserProfileDTO buildUserProfileDTO(People person, AccountCredentials account) {
        UserProfileDTO dto = new UserProfileDTO();

        dto.setPersonId(person.getPersonID());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setPersonType(person.getPersonType());
        dto.setDegreeLevel(person.getDegreeLevel());

        dto.setEmail(account.getEmail());
        dto.setUserName(account.getUserName());
        dto.setAccountStatus(account.getAccountStatus());

        return dto;
    }

    // *************************
    // SINGLE PLACE TO UPDATE PASSWORD IF PRESENT IN DTO
    // *************************
    private void updatePasswordIfPresent(AccountCredentials account, String rawPassword) {
        if (rawPassword != null && !rawPassword.isBlank()) {
            account.setPassword(passwordEncoder.encode(rawPassword));
        }
    }
}