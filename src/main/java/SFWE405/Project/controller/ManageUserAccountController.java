package SFWE405.Project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.service.ManageUserAccountService;

/*
 * @author Karri Fox
 *
 * Controller to allow the user to manage their account, such as changing their password, email, etc.
 * 
 */

@RestController
@RequestMapping("/api/accounts")
public class ManageUserAccountController {

    private final ManageUserAccountService accountService;

    public ManageUserAccountController(ManageUserAccountService accountService) {
        this.accountService = accountService;
    }

    // Create Account
    @PostMapping("/person/{personId}")
    public ResponseEntity<AccountCredentials> createAccount(
            @PathVariable Long personId,
            @RequestBody AccountCredentials account) {

        AccountCredentials created = accountService.createAccount(personId, account);
        return ResponseEntity.ok(created);
    }

    // Update Account
    @PutMapping("/{accountId}")
    public ResponseEntity<AccountCredentials> updateAccount(
            @PathVariable Long accountId,
            @RequestBody AccountCredentials account) {

        return ResponseEntity.ok(accountService.updateAccount(accountId, account));
    }

    // Get One
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountCredentials> getAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccount(accountId));
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<AccountCredentials>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Delete
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
