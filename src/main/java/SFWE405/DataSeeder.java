package SFWE405;

import java.time.LocalDate;
import java.time.LocalDateTime;

import SFWE405.Project.entity.*;
import SFWE405.Project.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            CourseAssignmentRepository courseAssignmentRepository,
            UniversityRequirementsRepository universityRequirementsRepository
    ) {
        return args -> {

            // 1. University
            University university = new University();
            university.setName("UofA");
            university.setLocation("Tucson");
            university = universityRepository.save(university);

            // 2. Student & Faculty Member (People)
            People student = new People();
            student.setFirstName("Brandon");
            student.setLastName("Sisco");
            student.setEmail("brandon@example.com");
            student.setPersonType(People.PersonType.STUDENT);
            student.setDegreeLevel(People.DegreeLevel.UNDERGRADUATE);
            student.setEnrolledAt(university);
            student = peopleRepository.save(student);

            People facultyMember = new People();
            facultyMember.setFirstName("Faculty");
            facultyMember.setLastName("Member");
            facultyMember.setEmail("faculty@example.com");
            facultyMember.setPersonType(People.PersonType.FACULTY);
            facultyMember.setEnrolledAt(university);
            facultyMember = peopleRepository.save(facultyMember);


            // 3. Credentials
            AccountCredentials credsI = new AccountCredentials();
            credsI.setUserName("testUser");
            credsI.setPassword("password123");
            credsI.setEmail("brandon@example.com");
            credsI.setPerson(student);
            credsI.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            credsI.setDateCreated(LocalDateTime.now());
            credsI = credentialsRepository.save(credsI);

            AccountCredentials credsII = new AccountCredentials();
            credsII.setUserName("facultyMember");
            credsII.setPassword("fac123");
            credsII.setEmail("faculty@example.com");
            credsII.setPerson(facultyMember);
            credsII.setAccountStatus(AccountCredentials.AccountStatus.ACTIVE);
            credsII.setDateCreated(LocalDateTime.now());
            credsII = credentialsRepository.save(credsII);

            //Link to student && facultyMember
            student.setAccountCredentials(credsI);
            student = peopleRepository.save(student); //authentication token requires link between person and credential

            facultyMember.setAccountCredentials(credsII);
            facultyMember = peopleRepository.save(facultyMember);

            // 4. Semester
            Semester semester = new Semester();
            semester.setSemesterYear(2026);
            semester.setSeason(Semester.Season.SPRING);
            semester = semesterRepository.save(semester);

            Semester fall2026 = new Semester();
            fall2026.setSemesterYear(2026);
            fall2026.setSeason(Semester.Season.FALL);
            fall2026 = semesterRepository.save(fall2026);

            // 5. Course
            Course course = new Course();
            course.setCourseCode("CSC355");
            course.setCourseName("Data Structures & Algorithms");
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

            //More Courses
            Course ece101Spring = new Course();
            ece101Spring.setCourseCode("ECE101");
            ece101Spring.setCourseName("Programming I");
            ece101Spring.setCourseType(Course.CourseType.LECTURE);
            ece101Spring.setSemester(semester);
            ece101Spring.setUniversity(university);
            ece101Spring.setUnitsAmount(3);
            ece101Spring.setUpperDivision(false);
            ece101Spring = courseRepository.save(ece101Spring);

            HomeworkAssignment ece101hw1 = new HomeworkAssignment();
            ece101hw1.setAssignmentName("Project #1");
            ece101hw1.setDueDate(LocalDate.now().plusDays(7));  // Must be future date due to @Future validation
            ece101hw1.setCourse(ece101Spring);
            homeworkAssignmentRepository.save(ece101hw1);

            HomeworkAssignment ece101hw2 = new HomeworkAssignment();
            ece101hw2.setAssignmentName("Project #2");
            ece101hw2.setDueDate(LocalDate.now().plusDays(14));  // Must be future date due to @Future validation
            ece101hw2.setCourse(ece101Spring);
            homeworkAssignmentRepository.save(ece101hw2);

            Course ece101Fall = new Course();
            ece101Fall.setCourseCode("ECE101");
            ece101Fall.setCourseName("Programming I");
            ece101Fall.setCourseType(Course.CourseType.LECTURE);
            ece101Fall.setSemester(fall2026);
            ece101Fall.setUniversity(university);
            ece101Fall.setUnitsAmount(3);
            ece101Fall.setUpperDivision(false);
            ece101Fall = courseRepository.save(ece101Fall);


            Course ece201Spring = new Course();
            ece201Spring.setCourseCode("ECE201");
            ece201Spring.setCourseName("Programming II");
            ece201Spring.setCourseType(Course.CourseType.LECTURE);
            ece201Spring.setSemester(semester);
            ece201Spring.setUniversity(university);
            ece201Spring.setUnitsAmount(3);
            ece201Spring.setUpperDivision(false);
            ece201Spring = courseRepository.save(ece201Spring);

            Course ece201Fall = new Course();
            ece201Fall.setCourseCode("ECE201");
            ece201Fall.setCourseName("Programming II");
            ece201Fall.setCourseType(Course.CourseType.LECTURE);
            ece201Fall.setSemester(fall2026);
            ece201Fall.setUniversity(university);
            ece201Fall.setUnitsAmount(3);
            ece201Fall.setUpperDivision(false);
            ece201Fall = courseRepository.save(ece201Fall);


            Course ece274aSpring = new Course();
            ece274aSpring.setCourseCode("ECE274A");
            ece274aSpring.setCourseName("Digital Logic");
            ece274aSpring.setCourseType(Course.CourseType.LECTURE);
            ece274aSpring.setSemester(semester);
            ece274aSpring.setUniversity(university);
            ece274aSpring.setUnitsAmount(4);
            ece274aSpring.setUpperDivision(false);
            ece274aSpring = courseRepository.save(ece274aSpring);

            Course ece274aFall = new Course();
            ece274aFall.setCourseCode("ECE274A");
            ece274aFall.setCourseName("Digital Logic");
            ece274aFall.setCourseType(Course.CourseType.LECTURE);
            ece274aFall.setSemester(fall2026);
            ece274aFall.setUniversity(university);
            ece274aFall.setUnitsAmount(4);
            ece274aFall.setUpperDivision(false);
            ece274aFall = courseRepository.save(ece274aFall);


            Course ece311Spring = new Course();
            ece311Spring.setCourseCode("ECE311");
            ece311Spring.setCourseName("Engineering Ethics and Contemporary Issues");
            ece311Spring.setCourseType(Course.CourseType.LECTURE);
            ece311Spring.setSemester(semester);
            ece311Spring.setUniversity(university);
            ece311Spring.setUnitsAmount(1);
            ece311Spring.setUpperDivision(true);
            ece311Spring = courseRepository.save(ece311Spring);

            Course ece311Fall = new Course();
            ece311Fall.setCourseCode("ECE311");
            ece311Fall.setCourseName("Engineering Ethics and Contemporary Issues");
            ece311Fall.setCourseType(Course.CourseType.LECTURE);
            ece311Fall.setSemester(fall2026);
            ece311Fall.setUniversity(university);
            ece311Fall.setUnitsAmount(1);
            ece311Fall.setUpperDivision(true);
            ece311Fall = courseRepository.save(ece311Fall);


            Course ece369aFall = new Course();
            ece369aFall.setCourseCode("ECE369A");
            ece369aFall.setCourseName("Fundamentals of Computer Organization");
            ece369aFall.setCourseType(Course.CourseType.LECTURE);
            ece369aFall.setSemester(fall2026);
            ece369aFall.setUniversity(university);
            ece369aFall.setUnitsAmount(4);
            ece369aFall.setUpperDivision(true);
            ece369aFall = courseRepository.save(ece369aFall);


            Course sfwe101Spring = new Course();
            sfwe101Spring.setCourseCode("SFWE101");
            sfwe101Spring.setCourseName("Introduction to Software Engineering");
            sfwe101Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe101Spring.setSemester(semester);
            sfwe101Spring.setUniversity(university);
            sfwe101Spring.setUnitsAmount(3);
            sfwe101Spring.setUpperDivision(false);
            sfwe101Spring = courseRepository.save(sfwe101Spring);

            Course sfwe101Fall = new Course();
            sfwe101Fall.setCourseCode("SFWE101");
            sfwe101Fall.setCourseName("Introduction to Software Engineering");
            sfwe101Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe101Fall.setSemester(fall2026);
            sfwe101Fall.setUniversity(university);
            sfwe101Fall.setUnitsAmount(3);
            sfwe101Fall.setUpperDivision(false);
            sfwe101Fall = courseRepository.save(sfwe101Fall);


            Course sfwe201Spring = new Course();
            sfwe201Spring.setCourseCode("SFWE201");
            sfwe201Spring.setCourseName("Software Engineering Sophomore Colloquium");
            sfwe201Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe201Spring.setSemester(semester);
            sfwe201Spring.setUniversity(university);
            sfwe201Spring.setUnitsAmount(1);
            sfwe201Spring.setUpperDivision(false);
            sfwe201Spring = courseRepository.save(sfwe201Spring);

            Course sfwe201Fall = new Course();
            sfwe201Fall.setCourseCode("SFWE201");
            sfwe201Fall.setCourseName("Software Engineering Sophomore Colloquium");
            sfwe201Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe201Fall.setSemester(fall2026);
            sfwe201Fall.setUniversity(university);
            sfwe201Fall.setUnitsAmount(1);
            sfwe201Fall.setUpperDivision(false);
            sfwe201Fall = courseRepository.save(sfwe201Fall);


            Course sfwe301Fall = new Course();
            sfwe301Fall.setCourseCode("SFWE301");
            sfwe301Fall.setCourseName("Software Requirements Analysis and Test");
            sfwe301Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe301Fall.setSemester(fall2026);
            sfwe301Fall.setUniversity(university);
            sfwe301Fall.setUnitsAmount(3);
            sfwe301Fall.setUpperDivision(true);
            sfwe301Fall = courseRepository.save(sfwe301Fall);


            Course sfwe303Spring = new Course();
            sfwe303Spring.setCourseCode("SFWE303");
            sfwe303Spring.setCourseName("Data Persistence");
            sfwe303Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe303Spring.setSemester(semester);
            sfwe303Spring.setUniversity(university);
            sfwe303Spring.setUnitsAmount(3);
            sfwe303Spring.setUpperDivision(true);
            sfwe303Spring = courseRepository.save(sfwe303Spring);


            Course sfwe402Spring = new Course();
            sfwe402Spring.setCourseCode("SFWE402");
            sfwe402Spring.setCourseName("DevSecOps");
            sfwe402Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe402Spring.setSemester(semester);
            sfwe402Spring.setUniversity(university);
            sfwe402Spring.setUnitsAmount(3);
            sfwe402Spring.setUpperDivision(true);
            sfwe402Spring = courseRepository.save(sfwe402Spring);


            Course sfwe403Fall = new Course();
            sfwe403Fall.setCourseCode("SFWE403");
            sfwe403Fall.setCourseName("Software Project Management");
            sfwe403Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe403Fall.setSemester(fall2026);
            sfwe403Fall.setUniversity(university);
            sfwe403Fall.setUnitsAmount(3);
            sfwe403Fall.setUpperDivision(true);
            sfwe403Fall = courseRepository.save(sfwe403Fall);


            Course sfwe405Spring = new Course();
            sfwe405Spring.setCourseCode("SFWE405");
            sfwe405Spring.setCourseName("Software Architecture and Design");
            sfwe405Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe405Spring.setSemester(semester);
            sfwe405Spring.setUniversity(university);
            sfwe405Spring.setUnitsAmount(3);
            sfwe405Spring.setUpperDivision(true);
            sfwe405Spring = courseRepository.save(sfwe405Spring);


            Course sfwe407Spring = new Course();
            sfwe407Spring.setCourseCode("SFWE407");
            sfwe407Spring.setCourseName("Foundations of Software Engineering");
            sfwe407Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe407Spring.setSemester(semester);
            sfwe407Spring.setUniversity(university);
            sfwe407Spring.setUnitsAmount(3);
            sfwe407Spring.setUpperDivision(true);
            sfwe407Spring = courseRepository.save(sfwe407Spring);


            Course sfwe409Spring = new Course();
            sfwe409Spring.setCourseCode("SFWE409");
            sfwe409Spring.setCourseName("Principles of Cloud Computing");
            sfwe409Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe409Spring.setSemester(semester);
            sfwe409Spring.setUniversity(university);
            sfwe409Spring.setUnitsAmount(3);
            sfwe409Spring.setUpperDivision(true);
            sfwe409Spring = courseRepository.save(sfwe409Spring);

            Course sfwe409Fall = new Course();
            sfwe409Fall.setCourseCode("SFWE409");
            sfwe409Fall.setCourseName("Principles of Cloud Computing");
            sfwe409Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe409Fall.setSemester(fall2026);
            sfwe409Fall.setUniversity(university);
            sfwe409Fall.setUnitsAmount(3);
            sfwe409Fall.setUpperDivision(true);
            sfwe409Fall = courseRepository.save(sfwe409Fall);


            Course sfwe410Spring = new Course();
            sfwe410Spring.setCourseCode("SFWE410");
            sfwe410Spring.setCourseName("Cloud-Native");
            sfwe410Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe410Spring.setSemester(semester);
            sfwe410Spring.setUniversity(university);
            sfwe410Spring.setUnitsAmount(3);
            sfwe410Spring.setUpperDivision(true);
            sfwe410Spring = courseRepository.save(sfwe410Spring);

            Course sfwe410Fall = new Course();
            sfwe410Fall.setCourseCode("SFWE410");
            sfwe410Fall.setCourseName("Cloud-Native");
            sfwe410Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe410Fall.setSemester(fall2026);
            sfwe410Fall.setUniversity(university);
            sfwe410Fall.setUnitsAmount(3);
            sfwe410Fall.setUpperDivision(true);
            sfwe410Fall = courseRepository.save(sfwe410Fall);


            Course sfwe411Fall = new Course();
            sfwe411Fall.setCourseCode("SFWE411");
            sfwe411Fall.setCourseName("Software for Industrial Control Systems");
            sfwe411Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe411Fall.setSemester(fall2026);
            sfwe411Fall.setUniversity(university);
            sfwe411Fall.setUnitsAmount(3);
            sfwe411Fall.setUpperDivision(true);
            sfwe411Fall = courseRepository.save(sfwe411Fall);


            Course sfwe491Spring = new Course();
            sfwe491Spring.setCourseCode("SFWE491");
            sfwe491Spring.setCourseName("Software Engineering Preceptor");
            sfwe491Spring.setCourseType(Course.CourseType.LECTURE);
            sfwe491Spring.setSemester(semester);
            sfwe491Spring.setUniversity(university);
            sfwe491Spring.setUnitsAmount(1);
            sfwe491Spring.setUpperDivision(true);
            sfwe491Spring = courseRepository.save(sfwe491Spring);

            Course sfwe491Fall = new Course();
            sfwe491Fall.setCourseCode("SFWE491");
            sfwe491Fall.setCourseName("Software Engineering Preceptor");
            sfwe491Fall.setCourseType(Course.CourseType.LECTURE);
            sfwe491Fall.setSemester(fall2026);
            sfwe491Fall.setUniversity(university);
            sfwe491Fall.setUnitsAmount(1);
            sfwe491Fall.setUpperDivision(true);
            sfwe491Fall = courseRepository.save(sfwe491Fall);

            // 6. Enrollment
            Enrollment enrollment = new Enrollment();
            enrollment.setPerson(student);
            enrollment.setCourse(course);
            enrollment.setStatus(Enrollment.EnrollmentStatus.COMPLETED);
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

            //12. Populated University Requirements
            UniversityRequirements req1 = new UniversityRequirements();
            req1.setRequirementDescription("Minimum 3.0 GPA for Software Engineering enrollment.");
            req1.setCategory(101L);
            req1.setUniversity(university);
            universityRequirementsRepository.save(req1);

            UniversityRequirements req2 = new UniversityRequirements();
            req2.setRequirementDescription("Must complete Software Programming II with a C or better.");
            req2.setCategory(101L);
            req2.setUniversity(university);
            universityRequirementsRepository.save(req2);

            UniversityRequirements req3 = new UniversityRequirements();
            req3.setRequirementDescription("Must complete 45 credit hours.");
            req3.setCategory(202L);
            req3.setUniversity(university);
            universityRequirementsRepository.save(req3);

            UniversityRequirements req4 = new UniversityRequirements();
            req4.setRequirementDescription("Must complete SFWE 405.");
            req4.setCategory(202L);
            req4.setUniversity(university);
            universityRequirementsRepository.save(req4);

            UniversityRequirements req5 = new UniversityRequirements();
            req5.setRequirementDescription("Submit official transcripts from all previous institutions.");
            req5.setCategory(202L);
            req5.setUniversity(university);
            universityRequirementsRepository.save(req5);

            UniversityRequirements req6 = new UniversityRequirements();
            req6.setRequirementDescription("Department Consent Required to enroll.");
            req6.setCategory(303L);
            req6.setUniversity(university);
            universityRequirementsRepository.save(req6);

            System.out.println("Database seeded successfully!");
        };
    }
}
