package SFWE405.Project.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import SFWE405.Project.entity.UniversityRequirements;
import SFWE405.Project.service.UniversityRequirementsService;


/*

Created By: Gavin Hernandez

Handles communications for Univerity Requirements

 */
@RestController
@RequestMapping("/api/university-requirements")
@CrossOrigin(origins = "http://localhost:3000")
public class UniversityRequirementsController {
    private final UniversityRequirementsService requirementsService;

    public  UniversityRequirementsController(UniversityRequirementsService requirementsService) {
        this.requirementsService = requirementsService;
    }

    @GetMapping
    public List<UniversityRequirements> getAll() {
        return requirementsService.getRequirements(null, null);
    }

    @PostMapping
    public UniversityRequirements add(@RequestBody UniversityRequirements requirements) {
        try{
            return requirementsService.addRequirements(requirements);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        requirementsService.deleteRequirements(id);
    }

}
