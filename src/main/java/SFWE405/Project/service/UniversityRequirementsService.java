package SFWE405.Project.service;

import SFWE405.Project.entity.University;
import SFWE405.Project.entity.UniversityRequirements;
import SFWE405.Project.repository.UniversityRepository;
import SFWE405.Project.repository.UniversityRequirementsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*

Created By: Gavin Hernandez

Service to manage requirements for a University

 */
@Service

public class UniversityRequirementsService {
    private final UniversityRequirementsRepository requirementsRepository;
    private final UniversityRepository universityRepository;

    public UniversityRequirementsService(UniversityRequirementsRepository requirementsRepository, UniversityRepository universityRepository) {
        this.requirementsRepository = requirementsRepository;
        this.universityRepository = universityRepository;
    }

    public List<UniversityRequirements> getRequirements(Long categoryId, Long universityId) {
        if(categoryId != null){
            return requirementsRepository.findByCategory(categoryId);
        }
        return requirementsRepository.findAll();
    }

    @Transactional
    public UniversityRequirements addRequirements(UniversityRequirements requirements) {
        Long incomingId = requirements.getUniversity().getUniversityId();
        University managedUni = universityRepository.findById(incomingId).orElseThrow(() -> new RuntimeException("University ID " + incomingId + " not found"));
        requirements.setUniversity(managedUni);
        return requirementsRepository.save(requirements);
    }

    @Transactional
    public UniversityRequirements updateRequirements(UniversityRequirements requirements) {
        return requirementsRepository.save(requirements);
    }

    @Transactional
    public void deleteRequirements(Long id) {
        requirementsRepository.deleteById(id);
    }
}
