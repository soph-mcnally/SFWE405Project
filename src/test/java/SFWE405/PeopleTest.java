package SFWE405;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import SFWE405.Project.entity.AccountCredentials;
import SFWE405.Project.entity.People;
import SFWE405.Project.entity.University;

class PeopleTest {

    @Test
    void testConstructorAndGetters() {
        People person = new People(
                "John",
                "Doe",
                People.PersonType.STUDENT,
                People.DegreeLevel.UNDERGRADUATE
        );

        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals(People.PersonType.STUDENT, person.getPersonType());
        assertEquals(People.DegreeLevel.UNDERGRADUATE, person.getDegreeLevel());
    }

    @Test
    void testSetters() {
        People person = new People();

        person.setFirstName("Jane");
        person.setLastName("Smith");
        person.setPersonType(People.PersonType.FACULTY);
        person.setDegreeLevel(People.DegreeLevel.GRADUATE);

        assertEquals("Jane", person.getFirstName());
        assertEquals("Smith", person.getLastName());
        assertEquals(People.PersonType.FACULTY, person.getPersonType());
        assertEquals(People.DegreeLevel.GRADUATE, person.getDegreeLevel());
    }

    @Test
    void testSetEnrolledAt() {
        People person = new People();
        University uni = new University();

        person.setEnrolledAt(uni);

        assertEquals(uni, person.getEnrolledAt());
    }

    @Test
    void testAccountCredentialsLink() {
        People person = new People();
        AccountCredentials credentials = new AccountCredentials();

        person.setAccountCredentials(credentials);

        assertEquals(credentials, person.getAccountCredentials());
    }

    @Test
    void testUniversityIdTransientField() {
        People person = new People();
        person.setUniversityId(10L);

        assertEquals(10L, person.getUniversityId());
    }
}
