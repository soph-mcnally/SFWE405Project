package SFWE405.Project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import SFWE405.Project.entity.*;
import SFWE405.Project.repository.*;

import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            UniversityRepository universityRepository,
            PeopleRepository peopleRepository,
            AccountCredentialsRepository credentialsRepository,
            SemesterRepository semesterRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository
    ) {
        return args -> {

            // 1. University
            University university = new University();
            university.setName("UofA");
            university.setLocation("Tucson");
            university = universityRepository.save(university);

            // 2. Student (People)
            People student = new People();
            student.setFirstName("Brandon");
            student.setLastName("Sisco");
            student.setEmail("brandon@example.com");
            student.setPersonType(People.PersonType.STUDENT);
            student.setDegreeLevel(People.DegreeLevel.UNDERGRADUATE);
            student.setEnrolledAt(university);
            student = peopleRepository.save(student);

            // 3. Credentials
            AccountCredentials creds = new AccountCredentials();
            creds.setUsername("testUser");
            creds.setPassword("password123");
            creds.setPerson(student);
            creds.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            credentialsRepository.save(creds);

            // 4. Semester
            Semester semester = new Semester();
            semester.setSemesterYear(2026);
            semester.setSeason(Semester.Season.SPRING);
            semester = semesterRepository.save(semester);

            // 5. Course
            Course course = new Course();
            course.setCourseCode("CSE405");
            course.setCourseName("Software Engineering");
            course.setCourseType(Course.CourseType.LECTURE);
            course.setSemester(semester);
            course.setUniversity(university);
            course.setUnitsAmount(3);
            course.setUpperDivision(true);
            course = courseRepository.save(course);

            // 6. Enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setPerson(student);
            enrollment.setCourse(course);
            enrollment.setStatus(Enrollment.EnrollmentStatus.ENROLLED);
            enrollment.setGrade("A");
            enrollment.setEnrolledDate(LocalDate.now());
            enrollmentRepository.save(enrollment);

            System.out.println("Database seeded successfully!");
        };
    }
}
