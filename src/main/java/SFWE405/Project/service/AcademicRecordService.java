package SFWE405.Project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import SFWE405.Project.entity.Enrollment;
import SFWE405.Project.repository.EnrollmentRepository;

/**
 * @author Brandon Sisco
 *
 * Service responsible for retrieving and building a student's academic record.
 *
 * This service gathers enrollment data associated with a student and returns
 * it as part of their academic history, including courses taken, grades, and
 * enrollment details.
 */
@Service
public class AcademicRecordService {

    private final EnrollmentRepository enrollmentRepository;

    public AcademicRecordService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAcademicRecord(Long personId) {
        return enrollmentRepository.findByPersonPersonID(personId);
    }
}
