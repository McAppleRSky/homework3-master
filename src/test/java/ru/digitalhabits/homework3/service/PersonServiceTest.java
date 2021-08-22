package ru.digitalhabits.homework3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.PersonFullResponse;
import ru.digitalhabits.homework3.model.PersonRequest;
import ru.digitalhabits.homework3.model.PersonShortResponse;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.*;import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@SpringBootTest
class PersonServiceTest {

    private PersonDao personDao;
    private PersonService personService;
    private DepartmentDao departmentDao;

    @BeforeEach
    void init(){
        this.personDao = mock(PersonDao.class);
        this.departmentDao = mock(DepartmentDao.class);
        this.personService = new PersonServiceImpl(personDao);
        this.personService.setDepartmentDao(departmentDao);
    }

    @Test
    void findAll() {
        // TODO: (V) NotImplemented
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);
        when(personDao.findAll())
                .thenReturn(range(0, count)
                        .mapToObj( i -> Person.with(nextInt())
                                .setFirstName(randomAlphabetic(7))
                                .setLastName(randomAlphabetic(7))
                                .setMiddleName(randomAlphabetic(7))
                                .setAge(nextInt())
                        ).collect(toList()));
        List<PersonShortResponse> persons = personService.findAll();
        assertEquals(count, persons.size());
    }

    @Test
    void findById() {
        // TODO: (V) NotImplemented
        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        String[] name = new String[3];
        for (int i = 0; i < 3; i++) {
            name[i] = randomAlphabetic(7);
        }
        int[] ints = new int[2];
        for (int i = 0; i < 2; i++) {
            ints[i] = nextInt();
        }
        when(personDao.findById(anyInt()))
                .thenReturn(Person.with(ints[ID])
                        .setFirstName(name[FIRST])
                        .setLastName(name[LAST])
                        .setMiddleName(name[MIDDLE])
                        .setAge(ints[AGE])
                        .setDepartment(Department.with(""))
                );
        PersonFullResponse actualResponse = personService.getById(ints[ID]);
        assertEquals(ints[ID], actualResponse.getId());
        assertEquals(name[LAST] + " " + name[FIRST] + " " + name[MIDDLE], actualResponse.getFullName());
    }

    @Test
    void create() {
        // TODO: (V) NotImplemented
        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        String[] name = new String[3];
        for (int i = 0; i < 3; i++) {
            name[i] = randomAlphabetic(7);
        }
        int[] ints = new int[2];
        for (int i = 0; i < 2; i++) {
            ints[i] = nextInt();
        }
        final PersonRequest request = new PersonRequest()
                .setFirstName(name[FIRST])
                .setLastName(name[LAST])
                .setMiddleName(name[MIDDLE])
                .setAge(ints[AGE])
        ;
        when( personDao.save( any(Person.class) ) )
                .thenReturn(Person.with(ints[ID])
                        .setFirstName(name[FIRST])
                        .setLastName(name[LAST])
                        .setMiddleName(name[MIDDLE])
                        .setAge(ints[AGE])
                        .setDepartment(Department.with("")));
        assertEquals(ints[ID], personService.create(request));
    }

    @Test
    void update() {
        // TODO: (V) NotImplemented
        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        String[] name = new String[3];
        for (int i = 0; i < 3; i++) {
            name[i] = randomAlphabetic(7);
        }
        int[] ints = new int[2];
        for (int i = 0; i < 2; i++) {
            ints[i] = nextInt();
        }
        when( personDao.findById( anyInt() ) )
                .thenReturn(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.update( ints[ID], new PersonRequest()
                    .setFirstName(name[FIRST])
                    .setLastName(name[LAST])
                    .setMiddleName(name[MIDDLE])
                    .setAge(ints[AGE]));
        });
        String[] msgPart = exception.getMessage().split(String.valueOf(ints[ID]));
        assertEquals(2, msgPart.length);
    }
    @Test
    void updateSimply() {
        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        String[] name = new String[3];
        for (int i = 0; i < 3; i++) {
            name[i] = randomAlphabetic(7);
        }
        int[] ints = new int[2];
        for (int i = 0; i < 2; i++) {
            ints[i] = nextInt();
        }
        when(personDao.findById(anyInt()))
                .thenReturn(Person.with(ints[ID])
                        .setFirstName(name[FIRST])
                        .setLastName(name[LAST])
                        .setMiddleName(name[MIDDLE])
                        .setAge(ints[AGE])
                        .setDepartment(Department.with(""))
                );
        assertNotNull( personService.update( ints[ID], new PersonRequest()
                .setFirstName(name[FIRST])
                .setLastName(name[LAST])
                .setMiddleName(name[MIDDLE])
                .setAge(ints[AGE]) ) );
    }
    @Test
    void delete() {
        // TODO: (V~) NotImplemented
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);

        int ID = 0, AGE = 1, FIRST = 0, LAST = 1, MIDDLE = 2;
        String[] name = new String[3];
        for (int i = 0; i < 3; i++) {
            name[i] = randomAlphabetic(7);
        }
        int[] ints = new int[2];
        for (int i = 0; i < 2; i++) {
            ints[i] = nextInt();
        }
        when(personDao.findById(anyInt()))
                .thenReturn(Person.with(ints[ID])
                        .setFirstName(name[FIRST])
                        .setLastName(name[LAST])
                        .setMiddleName(name[MIDDLE])
                        .setAge(ints[AGE])
                        .setDepartment(Department.with(""))
                );
        assertEquals(0, count);
    }

    @Test
    void addPersonToDepartment() {
        // TODO: (V) NotImplemented
        int departmentId = nextInt(), personId = nextInt();
        when( departmentDao.findById( anyInt() ) )
                .thenReturn(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.addPersonToDepartment( departmentId, personId);
        });
        String[] msgPart = exception.getMessage().split(String.valueOf(departmentId));
        assertEquals(2, msgPart.length);

        /**/
    }
    @Test
    void addPersonToDepartment0() {
        int departmentId = nextInt(), personId = nextInt();
        when( departmentDao.findById( anyInt() ) )
                .thenReturn(Department.with(randomAlphabetic(7)).setId(departmentId).setClosed(true));
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            personService.addPersonToDepartment( departmentId, personId);
        });
        String[] msgPart = exception.getMessage().split(String.valueOf(departmentId));
        assertEquals(2, msgPart.length);
    }
    @Test
    void addPersonToDepartment1() {
        int departmentId = nextInt(), personId = nextInt();
        when( departmentDao.findById( anyInt() ) )
                .thenReturn(Department.with(randomAlphabetic(7)).setId(departmentId).setClosed(false));
        when( personDao.findById( anyInt() ) )
                .thenReturn(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.addPersonToDepartment( departmentId, personId);
        });
        String[] msgPart = exception.getMessage().split(String.valueOf(personId));
        assertEquals(2, msgPart.length);
    }
    @Test
    void removePersonFromDepartment() {
        // TODO: (V) NotImplemented
        int departmentId = nextInt(), personId = nextInt();
        when( departmentDao.findById( anyInt() ) )
                .thenReturn(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            personService.removePersonFromDepartment( departmentId, personId);
        });
        String[] msgPart = exception.getMessage().split(String.valueOf(departmentId));
        assertEquals(2, msgPart.length);
    }
}
