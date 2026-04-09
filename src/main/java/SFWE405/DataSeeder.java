package SFWE405;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import SFWE405.Project.entity.*;
import SFWE405.Project.repository.*;

import java.time.LocalDateTime;
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
            creds.setUserName("testUser");
            creds.setPassword("password123");
            creds.setPerson(student);
            creds.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            creds.setDateCreated(LocalDateTime.now());
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

            // 5b. Second Course (not yet enrolled)
            Course course2 = new Course();
            course2.setCourseCode("SFWE401");
            course2.setCourseName("Software Assurance & Security");
            course2.setCourseType(Course.CourseType.LECTURE);
            course2.setSemester(semester);
            course2.setUniversity(university);
            course2.setUnitsAmount(3);
            course2.setUpperDivision(true);
            course2 = courseRepository.save(course2);

            // 6. Enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setPerson(student);
            enrollment.setCourse(course);
            enrollment.setStatus(Enrollment.EnrollmentStatus.ENROLLED);
            enrollment.setGrade("A");
            enrollment.setEnrolledDate(LocalDate.now());
            enrollmentRepository.save(enrollment);

            // 7. Admin (People)
            People admin = new People();
            admin.setFirstName("Sophie");
            admin.setLastName("McNally");
            admin.setEmail("sophie@example.com");
            admin.setPersonType(People.PersonType.ADMIN);
            admin.setDegreeLevel(People.DegreeLevel.GRADUATE);
            admin.setEnrolledAt(university);
            admin = peopleRepository.save(admin);

            // 8. Admin credentials
            AccountCredentials adminCreds = new AccountCredentials();
            adminCreds.setUserName("adminUser");
            adminCreds.setPassword("faculty123");
            adminCreds.setPerson(admin);
            adminCreds.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            adminCreds.setDateCreated(LocalDateTime.now());
            adminCreds = credentialsRepository.save(adminCreds);

            admin.setAccountCredentials(adminCreds);
            peopleRepository.save(admin);

            System.out.println("Database seeded successfully!");

        };
    }
}
