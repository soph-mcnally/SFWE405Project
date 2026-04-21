/**
 * @author Karri Fox
 *
 * Service to allow the user to manage their information, such as changing their password, email, etc.
 * 
 */

package SFWE405.Project.service;
import SFWE405.Project.entity.People;
import SFWE405.Project.entity.University;
import SFWE405.Project.repository.PeopleRepository;
import SFWE405.Project.repository.UniversityRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ManageUserService {

    private final PeopleRepository peopleRepository;
    private final UniversityRepository universityRepository;

    public ManageUserService(PeopleRepository peopleRepository, UniversityRepository universityRepository) {
        this.peopleRepository = peopleRepository;
        this.universityRepository = universityRepository;
    }

    // GET all
    public List<People> getAllPeople() 
    {
        return peopleRepository.findAll();
    }

    // GET by ID
    public Optional<People> getPersonById(Long id) 
    {
        return peopleRepository.findById(id);
    }

    // CREATE
    public People createPerson(People person) 
    {
        if (person.getUniversityId() == null) {
            throw new RuntimeException("University ID is required");
        }

        University university = universityRepository.findById(person.getUniversityId())
            .orElseThrow(() -> new RuntimeException("University not found"));

        person.setEnrolledAt(university);

        return peopleRepository.save(person);
    }

    // UPDATE
    public Optional<People> updatePerson(Long id, People updatedPerson) {
        return peopleRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(updatedPerson.getFirstName());
                    existing.setLastName(updatedPerson.getLastName());
                    existing.setEmail(updatedPerson.getEmail());
                    existing.setPersonType(updatedPerson.getPersonType());
                    existing.setDegreeLevel(updatedPerson.getDegreeLevel());

                    return peopleRepository.save(existing);
                });
    }

    // DELETE
    public boolean deletePerson(Long id) {
        if (!peopleRepository.existsById(id)) {
            return false;
        }
        peopleRepository.deleteById(id);
        return true;
    }
}
