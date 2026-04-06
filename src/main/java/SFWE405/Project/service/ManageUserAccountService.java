package SFWE405.Project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.entity.AccountCredentials.AccountStatus;
import SFWE405.Project.entity.People;
import SFWE405.Project.repository.AccountCredentialsRepository;
import SFWE405.Project.repository.PeopleRepository;

/**
 * @author Karri Fox
 *
 * Service to allow the user to manage their user account
 * 
 */

@Service
public class ManageUserAccountService {

    private final AccountCredentialsRepository accountRepository;
    private final PeopleRepository personRepository;

    public ManageUserAccountService(AccountCredentialsRepository accountRepository,
                          PeopleRepository personRepository) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
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

    // Update Account
    public AccountCredentials updateAccount(Long accountId, AccountCredentials updatedAccount) {
        AccountCredentials existing = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existing.setUserName(updatedAccount.getUserName());
        existing.setPassword(updatedAccount.getPassword());
        existing.setAccountStatus(updatedAccount.getAccountStatus());

        return accountRepository.save(existing);
    }

    // Get Account
    public AccountCredentials getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // Get All Accounts
    public List<AccountCredentials> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Delete Account
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}