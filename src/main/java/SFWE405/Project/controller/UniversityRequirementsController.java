package SFWE405.Project.controller;

import SFWE405.Project.entity.UniversityRequirements;
import SFWE405.Project.service.UniversityRequirementsService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/*

Created By: Gavin Hernandez

Handles communications for UniverityRequirements

 */
@RestController
@RequestMapping("/api/university-requirements")
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
        return requirementsService.addRequirements(requirements);
    }
}
