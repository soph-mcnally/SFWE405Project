package SFWE405.Project.controller;

import SFWE405.Project.entity.People;
import SFWE405.Project.repository.PeopleRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PeopleController {
    PeopleRepository peopleRepository;
   
    //constructor injection -> no @Autowired needed
    public PeopleController(PeopleRepository peopleRepository) {
      this.peopleRepository = peopleRepository;
    }
  
    // GET all people
    @GetMapping
    public List<People> getAllPeople() {
        return peopleRepository.findAll();
    }

    // GET person by ID
    @GetMapping("/{id}")
    public ResponseEntity<People> getPersonById(@PathVariable Long id) {
        Optional<People> person = peopleRepository.findById(id);

        return person
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE new person
    @PostMapping
    public People createPerson(@RequestBody People person) {
        return peopleRepository.save(person);
    }

    // UPDATE person
    @PutMapping("/{id}")
    public ResponseEntity<People> updatePerson(
            @PathVariable Long id,
            @RequestBody People updatedPerson) {

        return peopleRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updatedPerson.getFirstName());
                    existing.setLastName(updatedPerson.getLastName());
                    existing.setEnrolledAt(updatedPerson.getEnrolledAt());
                    existing.setPersonType(updatedPerson.getPersonType());
                    existing.setDegreeLevel(updatedPerson.getDegreeLevel());
                    People saved = peopleRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE person
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (!peopleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        peopleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
