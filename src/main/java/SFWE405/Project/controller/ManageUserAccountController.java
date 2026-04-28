/*
 * @author Karri Fox
 *
 * Controller to allow the user to manage their account, such as changing 
 * their password, email, etc.
 * 
 */

package SFWE405.Project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SFWE405.Project.dto.AccountDTO;
import SFWE405.Project.dto.UpdateUserProfileDTO;
import SFWE405.Project.dto.UserProfileDTO;
import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.entity.People;
import SFWE405.Project.mapper.UserMapper;
import SFWE405.Project.service.ManageUserAccountService;
import SFWE405.Project.service.ManageUserService;


@RestController
@RequestMapping("/api/manageUserAccounts")
@CrossOrigin(origins = "http://localhost:3000")
public class ManageUserAccountController {

    private final ManageUserAccountService accountService;
    private final ManageUserService manageUserService;
    private final UserMapper userMapper;

    public ManageUserAccountController(ManageUserAccountService accountService, ManageUserService manageUserService) {
        this.accountService = accountService;
        this.manageUserService = manageUserService;
        this.userMapper = new UserMapper();
    }

    // Create Account
    @PostMapping("/person/{personId}")
    public ResponseEntity<AccountCredentials> createAccount(
            @PathVariable Long personId,
            @RequestBody AccountCredentials account) {

        AccountCredentials created = accountService.createAccount(personId, account);
        return ResponseEntity.ok(created);
    }

    // Get One
    @GetMapping("/{credentialsId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long credentialsId) {
        AccountCredentials account = accountService.getAccount(credentialsId);
        return ResponseEntity.ok(UserMapper.toAccountDTO(account));
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<AccountCredentials>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Delete
    @DeleteMapping("/{credentialsId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long credentialsId) {
        accountService.deleteAccount(credentialsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/people/{id}")
    public ResponseEntity<People> getPerson(@PathVariable Long id) {
        return manageUserService.getPersonById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/people/{id}")
    public ResponseEntity<People> updatePerson(
            @PathVariable Long id,
            @RequestBody People person) {

        return manageUserService.updatePerson(id, person)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile/{personId}")
    public ResponseEntity<UserProfileDTO> updateUserProfile(
            @PathVariable Long personId,
            @RequestBody UpdateUserProfileDTO dto) {

        return ResponseEntity.ok(accountService.updateUserProfile(personId, dto));
    }
}
