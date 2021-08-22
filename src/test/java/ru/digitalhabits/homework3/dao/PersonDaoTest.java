package ru.digitalhabits.homework3.dao;

import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;

import java.util.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ComponentScan(basePackages = "ru.digitalhabits.homework3")
class PersonDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonDao personDao;

    @Test
    void createWhenEntityManagerIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new TestEntityManager(null))
                .withMessageContaining("EntityManagerFactory must not be null");
    }

    @Test
    void findById() {
        // TODO: (V) NotImplemented
        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        int[] ints = new int[2];
        for (int i = 0; i < 2; i++) {
            ints[i] = nextInt();
        }
        String[] name = new String[3];
        for (int i = 0; i < 3; i++) {
            name[i] = randomAlphabetic(7);
        }
        String querySrc = "insert into person (id, first_name, last_name, middle_name, age) values (:id, :first_name, :last_name, :middle_name, :age);";
        entityManager.getEntityManager().createNativeQuery(querySrc)
                .setParameter("id",         ints[ID])
                .setParameter("first_name", name[FIRST])
                .setParameter("last_name",  name[LAST])
                .setParameter("middle_name",name[MIDDLE])
                .setParameter("age",        ints[AGE])
                .executeUpdate();
        Person personActual = personDao.findById(ints[ID]);
        assertNotNull(personActual);
        assertEquals(ints[ID],    personActual.getId());
        assertEquals(name[FIRST], personActual.getFirstName());
        assertEquals(name[LAST],  personActual.getLastName());
        assertEquals(name[MIDDLE],personActual.getMiddleName());
        assertEquals(ints[AGE],   personActual.getAge());
    }

    @Test
    void findAll() {
        // TODO: (V) NotImplemented
        Map<Integer, Person> testValues = new HashMap<>();
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);

        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        int[] ints = new int[2];
        String[] name = new String[3];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 2; j++) {
                ints[j] = nextInt();
            }
            for (int j = 0; j < 3; j++) {
                name[j] = randomAlphabetic(7);
            }
            Person personPlain = Person.with(ints[ID]);
            personPlain.setFirstName(name[FIRST]);
            personPlain.setLastName(name[LAST]);
            personPlain.setMiddleName(name[MIDDLE]);
            personPlain.setAge(ints[AGE]);
            testValues.put(ints[ID], personPlain);
        }
        String querySrc = "insert into person (id, first_name, last_name, middle_name, age) values (:id, :first_name, :last_name, :middle_name, :age);";
        for (Map.Entry<Integer, Person> personPlainEntry : testValues.entrySet()) {
            entityManager.getEntityManager().createNativeQuery(querySrc)
                    .setParameter("id",         personPlainEntry.getValue().getId())
                    .setParameter("first_name", personPlainEntry.getValue().getFirstName())
                    .setParameter("last_name",  personPlainEntry.getValue().getLastName())
                    .setParameter("middle_name",personPlainEntry.getValue().getMiddleName())
                    .setParameter("age",        personPlainEntry.getValue().getAge())
                    .executeUpdate();
        }
        List<Person> personsActual = personDao.findAll();
        assertNotNull(personsActual);
        assertFalse(personsActual.isEmpty());
        assertEquals(count, personsActual.size());
        for (Person personCurrent : personsActual) {
            Person personPlainStored = testValues.remove(personCurrent.getId());
            assertEquals(personCurrent.getId(), personPlainStored.getId());
            assertEquals(personCurrent.getFirstName(), personPlainStored.getFirstName());
            assertEquals(personCurrent.getLastName(),  personPlainStored.getLastName());
            assertEquals(personCurrent.getMiddleName(),personPlainStored.getMiddleName());
            assertEquals(personCurrent.getAge(), personPlainStored.getAge());
        }
        assertTrue(testValues.isEmpty());
    }

    @Test
    void update() {
        // TODO: (V) NotImplemented
        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        int[] ints = new int[2];
        for (int i = 0; i < 2; i++) {
            ints[i] = nextInt();
        }
        String[] name = new String[3];
        for (int i = 0; i < 3; i++) {
            name[i] = randomAlphabetic(7);
        }
        Person personPlain = Person.with(ints[ID]);
        personPlain.setFirstName(name[FIRST]);
        personPlain.setLastName(name[LAST]);
        personPlain.setMiddleName(name[MIDDLE]);
        personPlain.setAge(ints[AGE]);

        String querySrc = "insert into person (id, first_name, last_name, middle_name, age) values (:id, :first_name, :last_name, :middle_name, :age);";
        entityManager.getEntityManager().createNativeQuery(querySrc)
                .setParameter("id",         personPlain.getId())
                .setParameter("first_name", personPlain.getFirstName())
                .setParameter("last_name",  personPlain.getLastName())
                .setParameter("middle_name",personPlain.getMiddleName())
                .setParameter("age",        personPlain.getAge())
                .executeUpdate();
        String firstnameAfterUpdate = randomAlphabetic(7);
        personPlain.setFirstName(firstnameAfterUpdate);
        Person personUpdated = personDao.update(personPlain);
        assertNotNull(personUpdated);
        assertNotEquals(name[FIRST], personUpdated.getFirstName());
        assertEquals(personPlain.getFirstName(), personUpdated.getFirstName());
    }

    @Test
    void delete() {
        // TODO: (V) NotImplemented
        Map<Integer, String> testValues = new HashMap<>();
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);
        int targetI = min + random.nextInt(count-min) - 1;

        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        int[][] ints = new int[count][2];
        String[][] name = new String[count][3];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 2; j++) {
                ints[i][j] = nextInt();
            }
            for (int j = 0; j < 3; j++) {
                name[i][j] = randomAlphabetic(7);
            }
        }
        String querySrc = "insert into person (id, first_name, last_name, middle_name, age) values (:id, :first_name, :last_name, :middle_name, :age);";
        for (int i = 0; i < count; i++) {
            entityManager.getEntityManager().createNativeQuery(querySrc)
                    .setParameter("id",         ints[i][ID])
                    .setParameter("first_name", name[i][FIRST])
                    .setParameter("last_name",  name[i][LAST])
                    .setParameter("middle_name",name[i][MIDDLE])
                    .setParameter("age",        ints[i][AGE])
                    .executeUpdate();
        }

        querySrc = "select * from person;";
        assertEquals(count, entityManager.getEntityManager().createNativeQuery(querySrc).getResultList().size());
        Person personDeleted = personDao.delete(ints[targetI][ID]);
        assertEquals(ints[targetI][ID], personDeleted.getId());
        assertEquals(name[targetI][FIRST], personDeleted.getFirstName());
        assertEquals(name[targetI][LAST], personDeleted.getLastName());
        assertEquals(name[targetI][MIDDLE], personDeleted.getMiddleName());
        assertEquals(ints[targetI][AGE], personDeleted.getAge());
        assertEquals(count-1, entityManager.getEntityManager().createNativeQuery(querySrc).getResultList().size());
    }
}