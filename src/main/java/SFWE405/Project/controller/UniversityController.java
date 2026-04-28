package SFWE405.Project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import SFWE405.Project.entity.University;
import SFWE405.Project.repository.UniversityRepository;

@RestController
@RequestMapping("/api/universities")
@CrossOrigin(origins = "http://localhost:3000")
public class UniversityController {

    @Autowired
    private UniversityRepository universitiesRepository;

    // GET all universities
    @GetMapping
    public List<University> getAllUniversities() {
        return universitiesRepository.findAll();
    }

    // GET university by ID
    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable Long id) {
        Optional<University> university = universitiesRepository.findById(id);

        return university
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE new university
    @PostMapping
    public University createUniversity(@RequestBody University university) {
        return universitiesRepository.save(university);
    }

    // UPDATE university
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(
            @PathVariable Long id,
            @RequestBody University updatedUniversity) {

        return universitiesRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedUniversity.getName());
                    existing.setLocation(updatedUniversity.getLocation());
                    University saved = universitiesRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE university
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        if (!universitiesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        universitiesRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}