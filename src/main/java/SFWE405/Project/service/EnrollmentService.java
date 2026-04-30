package SFWE405.Project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SFWE405.Project.entity.Enrollment;
import SFWE405.Project.entity.People;
import org.springframework.stereotype.Service;

import SFWE405.Project.dto.EnrollCoursesResponse;
import SFWE405.Project.entity.Course;
import SFWE405.Project.entity.Semester;
import SFWE405.Project.repository.CourseRepository;
import SFWE405.Project.repository.EnrollmentRepository;
import SFWE405.Project.repository.PeopleRepository;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final PeopleRepository peopleRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            PeopleRepository peopleRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.peopleRepository = peopleRepository;
        this.courseRepository = courseRepository;
    }

    public List<Semester> getAvailableSemesters(Long personId) {
        People person = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        List<Course> courses = courseRepository.findByUniversityUniversityId(
                person.getEnrolledAt().getUniversityId());

        List<Semester> semesters = new ArrayList<>();

        for (Course course : courses) {
            Semester semester = course.getSemester();

            if (!semesters.contains(semester)) {
                semesters.add(semester);
            }
        }

        return semesters;
    }

    public List<Course> getCoursesForSemester(Long personId, Long semesterId, String search) {
        People person = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        List<Course> courses = courseRepository.findByUniversityUniversityIdAndSemesterSemesterId(
                person.getEnrolledAt().getUniversityId(),
                semesterId);

        if (search == null || search.trim().isEmpty()) {
            return courses;
        }

        String keyword = search.trim().toLowerCase();

        return courses.stream()
                .filter(course ->
                        course.getCourseCode().toLowerCase().contains(keyword)
                                || course.getCourseName().toLowerCase().contains(keyword)
                )
                .toList();
    }

    public List<Course> getEnrolledCourses(Long personId) {
        List<Enrollment> enrollments = enrollmentRepository.findByPersonPersonID(personId);

        return enrollments.stream()
                .filter(enrollment -> enrollment.getStatus() == Enrollment.EnrollmentStatus.ENROLLED)
                .map(Enrollment::getCourse)
                .toList();
    }

    public List<Course> getCompletedCourses(Long personId) {
        List<Enrollment> enrollments = enrollmentRepository.findByPersonPersonID(personId);

        return enrollments.stream()
                .filter(enrollment -> enrollment.getStatus() == Enrollment.EnrollmentStatus.COMPLETED)
                .map(Enrollment::getCourse)
                .toList();
    }

    public EnrollCoursesResponse enrollStudentInCourses(Long personId, List<Long> courseIds) {
        People person = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        EnrollCoursesResponse response = new EnrollCoursesResponse();
        List<Long> enrolledCourseIds = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        List<Enrollment> existingEnrollments = enrollmentRepository.findByPersonPersonID(personId);

        Map<Long, Integer> semesterUnits = new HashMap<>();

        for (Enrollment enrollment : existingEnrollments) {
            if (enrollment.getStatus() == Enrollment.EnrollmentStatus.ENROLLED) {
                Long semesterId = enrollment.getCourse().getSemester().getSemesterId();
                int units = enrollment.getCourse().getUnitsAmount();

                semesterUnits.put(
                        semesterId,
                        semesterUnits.getOrDefault(semesterId, 0) + units
                );
            }
        }

        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId).orElse(null);

            if (course == null) {
                errors.add("Course not found: " + courseId);
                continue;
            }

            boolean existingEnrollment = enrollmentRepository
                    .findByPersonPersonIDAndCourseCourseId(personId, courseId)
                    .isPresent();

            if (existingEnrollment) {
                errors.add("Already enrolled in or completed course ID: " + courseId);
                continue;
            }

            Long semesterId = course.getSemester().getSemesterId();
            int currentUnitsForSemester = semesterUnits.getOrDefault(semesterId, 0);
            int courseUnits = course.getUnitsAmount();

            if (currentUnitsForSemester + courseUnits > 20) {
                errors.add("Cannot enroll in " + course.getCourseCode()
                        + ". Maximum allowed units is 20 for "
                        + course.getSemester().getSeason().name()
                        + " "
                        + course.getSemester().getSemesterYear()
                        + ".");
                continue;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setPerson(person);
            enrollment.setCourse(course);
            enrollment.setStatus(Enrollment.EnrollmentStatus.ENROLLED);
            enrollment.setEnrolledDate(java.time.LocalDate.now());

            enrollmentRepository.save(enrollment);
            enrolledCourseIds.add(courseId);

            semesterUnits.put(semesterId, currentUnitsForSemester + courseUnits);
        }

        response.setEnrolledCourseIds(enrolledCourseIds);
        response.setErrors(errors);

        return response;
    }

    public void unenrollStudentFromCourse(Long personId, Long courseId) {
        Enrollment enrollment = enrollmentRepository
                .findByPersonPersonIDAndCourseCourseId(personId, courseId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (enrollment.getStatus() != Enrollment.EnrollmentStatus.ENROLLED) {
            throw new RuntimeException("Only currently enrolled courses can be dropped");
        }

        enrollmentRepository.delete(enrollment);
    }
}