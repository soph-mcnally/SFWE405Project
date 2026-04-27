package SFWE405.Project.service;

import java.util.ArrayList;
import java.util.List;

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
                .map(Enrollment::getCourse)
                .toList();
    }

    public EnrollCoursesResponse enrollStudentInCourses(Long personId, List<Long> courseIds) {
        People person = peopleRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        EnrollCoursesResponse response = new EnrollCoursesResponse();
        List<Long> enrolledCourseIds = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (Long courseId : courseIds) {
            Course course = courseRepository.findById(courseId).orElse(null);

            if (course == null) {
                errors.add("Course not found: " + courseId);
                continue;
            }

            boolean alreadyEnrolled = enrollmentRepository
                    .findByPersonPersonIDAndCourseCourseId(personId, courseId)
                    .isPresent();

            if (alreadyEnrolled) {
                errors.add("Already enrolled in course ID: " + courseId);
                continue;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setPerson(person);
            enrollment.setCourse(course);
            enrollment.setStatus(Enrollment.EnrollmentStatus.ENROLLED);
            enrollment.setEnrolledDate(java.time.LocalDate.now());

            enrollmentRepository.save(enrollment);
            enrolledCourseIds.add(courseId);
        }

        response.setEnrolledCourseIds(enrolledCourseIds);
        response.setErrors(errors);

        return response;
    }
}