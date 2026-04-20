package SFWE405;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.entity.Course;
import SFWE405.Project.entity.Enrollment;
import SFWE405.Project.entity.HomeworkAssignment;
import SFWE405.Project.entity.CourseAssignment;
import SFWE405.Project.entity.People;
import SFWE405.Project.entity.Semester;
import SFWE405.Project.entity.University;
import SFWE405.Project.repository.AccountCredentialsRepository;
import SFWE405.Project.repository.CourseRepository;
import SFWE405.Project.repository.EnrollmentRepository;
import SFWE405.Project.repository.HomeworkAssignmentRepository;
import SFWE405.Project.repository.PeopleRepository;
import SFWE405.Project.repository.SemesterRepository;
import SFWE405.Project.repository.UniversityRepository;
import SFWE405.Project.repository.CourseAssignmentRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(
            UniversityRepository universityRepository,
            PeopleRepository peopleRepository,
            AccountCredentialsRepository credentialsRepository,
            SemesterRepository semesterRepository,
            CourseRepository courseRepository,
            EnrollmentRepository enrollmentRepository,
            HomeworkAssignmentRepository homeworkAssignmentRepository,
            CourseAssignmentRepository courseAssignmentRepository
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
            creds.setEmail("brandon@example.com");
            creds.setPerson(student);
            creds.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            creds.setDateCreated(LocalDateTime.now());
            creds = credentialsRepository.save(creds);

            //Link to student
            student.setAccountCredentials(creds);
            student = peopleRepository.save(student); //authentication token requires link between person and credential

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
            adminCreds.setEmail("sophie@example.com");
            adminCreds.setPerson(admin);
            adminCreds.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            adminCreds.setDateCreated(LocalDateTime.now());
            adminCreds = credentialsRepository.save(adminCreds);

            admin.setAccountCredentials(adminCreds);
            peopleRepository.save(admin);
            //9. Homework Assignment (tied to course student is enrolled in)
            HomeworkAssignment hw1 = new HomeworkAssignment();
            hw1.setAssignmentName("Project Phase 1");
            hw1.setDueDate(LocalDate.now().plusDays(7));  // Must be future date due to @Future validation
            hw1.setCourse(course);
            homeworkAssignmentRepository.save(hw1);

            HomeworkAssignment hw2 = new HomeworkAssignment();
            hw2.setAssignmentName("Project Phase 2");
            hw2.setDueDate(LocalDate.now().plusDays(14));
            hw2.setCourse(course);
            homeworkAssignmentRepository.save(hw2);

            //10. Faculty Creation & Credetials for Faculty
            People faculty = new People("Dr. Thomas", "Cerny", "tom@arizona.edu", People.PersonType.FACULTY, null);
            faculty.setEnrolledAt(university); //Propbably need to get rid of this requirement
            faculty = peopleRepository.save(faculty);

            AccountCredentials facultyCreds = new AccountCredentials();
            facultyCreds.setUserName("drCerny");
            facultyCreds.setPassword("securePass456");
            facultyCreds.setEmail("cerny@example.com");
            facultyCreds.setPerson(faculty);
            facultyCreds.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            facultyCreds.setDateCreated(LocalDateTime.now());
            facultyCreds = credentialsRepository.save(facultyCreds);
            
             //Link to Faculty
             faculty.setAccountCredentials(facultyCreds);
             faculty = peopleRepository.save(faculty); //authetication token requires link between person and credential

            //11. Course Assignment for Faculty
            CourseAssignment courseAssignment = new CourseAssignment(faculty, course);
            courseAssignmentRepository.save(courseAssignment);

            System.out.println("Database seeded successfully!");

        };
    }
}
