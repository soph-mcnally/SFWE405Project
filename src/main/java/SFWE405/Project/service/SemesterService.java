package SFWE405.Project.service;

import SFWE405.Project.dto.AvailableSemesterResponse;
import SFWE405.Project.entity.Course;
import SFWE405.Project.entity.Semester;
import SFWE405.Project.entity.University;
import SFWE405.Project.repository.CourseRepository;
import SFWE405.Project.repository.SemesterRepository;
import SFWE405.Project.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final UniversityRepository universityRepository;

    public SemesterService(SemesterRepository semesterRepository,
                           CourseRepository courseRepository, UniversityRepository universityRepository){
        this.semesterRepository = semesterRepository;
        this.courseRepository = courseRepository;
        this.universityRepository = universityRepository;
    }

    // semester CRUD
    public List<AvailableSemesterResponse> getAllSemesters(){
        return semesterRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AvailableSemesterResponse getSemesterById(Long id){
        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        return toResponse(semester);
    }

    public AvailableSemesterResponse createSemester(Semester semester){
        Semester saved = semesterRepository.save(semester);
        return toResponse(saved);
    }

    public AvailableSemesterResponse updateSemester(Long id, Semester updatedSemester){
        Semester existing = semesterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        existing.setSemesterYear(updatedSemester.getSemesterYear());
        existing.setSeason(updatedSemester.getSeason());

        Semester saved = semesterRepository.save(existing);
         return toResponse(saved);
    }
    public void deleteSemester(Long id){
        semesterRepository.deleteById(id);
    }

    // manage course by semester
    public List<Course> getCoursesBySemester(Long semesterId){
        semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        return courseRepository.findBySemesterSemesterId(semesterId);   // What is this
    }

    public Course addCourseToSemester(Long semesterId, Course course) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        University university = universityRepository.findById(course.getUniversity().getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));
        boolean duplicate = courseRepository.findBySemesterSemesterId(semesterId)
                .stream()
                .anyMatch(c -> c.getCourseCode().equalsIgnoreCase(course.getCourseCode()));

        if (duplicate) {
            throw new RuntimeException("Course with code " + course.getCourseCode() + " already exists in this semester");
        }
        course.setSemester(semester);
        course.setUniversity(university);
        return courseRepository.save(course);
    }

    public Course updateCourseInSemester(Long semesterId, Long courseId, Course updatedCourse){
        semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        Course existing = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (!existing.getSemester().getSemesterId().equals(semesterId)){
            throw new RuntimeException("Course does not belong to this semester");
        }

        existing.setCourseCode(updatedCourse.getCourseCode());
        existing.setCourseName(updatedCourse.getCourseName());
        existing.setCourseType(updatedCourse.getCourseType());
        existing.setUnitsAmount(updatedCourse.getUnitsAmount());
        existing.setUpperDivision(updatedCourse.getUpperDivision());

        return courseRepository.save(existing);
    }

    public void deleteCourseFromSemester(Long semesterId, Long courseId){
        semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Semester not found"));
        Course existing = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (!existing.getSemester().getSemesterId().equals(semesterId)){
            throw new RuntimeException("Course does not belong to this semester");
        }

        courseRepository.deleteById(courseId);
    }

    // helper
    private AvailableSemesterResponse toResponse(Semester semester){
        AvailableSemesterResponse response = new AvailableSemesterResponse();
        response.setId(semester.getSemesterId());
        response.setSeason(semester.getSeason().name());
        response.setSemesterYear(semester.getSemesterYear());
        return response;
    }
}
