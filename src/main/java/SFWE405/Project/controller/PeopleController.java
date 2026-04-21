/*
 * @author: Brandon Sisco
 * @UpdatedAuthor: Karri Fox
 *
 * Controller to allow the user to manage their account, such as changing their password, email, etc.
 * 
 */
package SFWE405.Project.controller;

import SFWE405.Project.entity.People;
import SFWE405.Project.service.ManageUserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final ManageUserService manageUserService;

    public PeopleController(ManageUserService manageUserService) {
        this.manageUserService = manageUserService;
    }

    // GET all
    @GetMapping("/all")
    public List<People> getAllPeople() {
        return manageUserService.getAllPeople();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<People> getPersonById(@PathVariable Long id) {
        return manageUserService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public People createPerson(@RequestBody People person) {
        return manageUserService.createPerson(person);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<People> updatePerson(
            @PathVariable Long id,
            @RequestBody People updatedPerson) {

        return manageUserService.updatePerson(id, updatedPerson)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean deleted = manageUserService.deletePerson(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
