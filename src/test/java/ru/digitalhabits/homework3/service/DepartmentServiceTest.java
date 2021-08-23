package ru.digitalhabits.homework3.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest(classes = DepartmentServiceTest.DepartmentServiceConfiguration.class)
class DepartmentServiceTest {

    private DepartmentDao departmentDao;
    private DepartmentService departmentService;
    private PersonService personService;

    @BeforeEach
    void init(){
        this.departmentDao = mock(DepartmentDao.class);
        this.personService = mock(PersonService.class);
        this.departmentService = new DepartmentServiceImpl(departmentDao, personService);
    }

    @Test
    void findAll() {
        // TODO: (V) NotImplemented
        // Spy
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);
        when(departmentDao.findAll())
                .thenReturn(range(0, count)
                                .mapToObj( i -> Department.with(randomAlphabetic(7)) )
                                .collect(toList())
        );
        final List<DepartmentShortResponse> departments = departmentService.findAll();
        assertEquals(count, departments.size());
    }

    @Test
    void findById() {
        // TODO: (V) NotImplemented
        int id = nextInt();
        String name = randomAlphabetic(7);
        when(departmentDao.findById(anyInt()))
                .thenReturn(Department.with(name).setId(id).setClosed(false).setPersons(new ArrayList<>()));
        DepartmentFullResponse actualResponse = departmentService.getById(id);
        assertEquals(name, actualResponse.getName());
        assertEquals(id, actualResponse.getId());
    }

    @Test
    void create() {
        // Spy
        // TODO: (V) NotImplemented
        String name = randomAlphabetic(7);
        int id = nextInt();
        final DepartmentRequest request = new DepartmentRequest().setName(name);
        final Department department = Department.with(request.getName()).setId(id);
        when( departmentDao.save( any(Department.class) ) )
                .thenReturn(department);
        assertEquals(id, departmentService.create(request));
    }

    @Test
    void update() {
        // TODO: (V) NotImplemented
        int id = nextInt();
        String name = randomAlphabetic(7);
        when( departmentDao.findById( anyInt() ) )
                .thenReturn(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            departmentService.update(id, new DepartmentRequest().setName(name));
        });
        String[] msgPart = exception.getMessage().split(String.valueOf(id));
        assertEquals(2, msgPart.length);
    }
    @Test
    void updateSimply() {
        int id = nextInt();
        String name = randomAlphabetic(7);
        when( departmentDao.findById( anyInt() ) )
                .thenReturn(Department.with(randomAlphabetic(7))
                        .setId(id).setClosed(false)
                        .setPersons(new ArrayList<>()));
        assertNotNull(departmentService.update(id, new DepartmentRequest().setName(name)));
    }

    @Test
    void delete() {
        // TODO: (v) NotImplemented
        int id = nextInt();
        String name = randomAlphabetic(7);
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);

        List<Person> persons = range(0, count)
                .mapToObj(i -> Person.with(nextInt()))
                .collect(toList());
        ConcurrentHashMap<Integer, Person> testPersons = new ConcurrentHashMap<>();
        for (Person person : persons) {
            testPersons.put(person.getId(), person);
        }
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(id, arg0);
            return null;
        }).when(departmentDao).delete(anyInt());
        doAnswer(invocation -> {  //NOVA
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            assertEquals(id, arg0);
            testPersons.remove(arg1);
            return null;
        }).when(personService).removePersonFromDepartment(anyInt(), anyInt());
        when(departmentDao.findById(anyInt()))
                .thenReturn(
                        Department.with(name).setId(id).setClosed(false).setPersons(persons)
                );
        assertEquals(count, testPersons.size());
        departmentService.delete(id);
        assertEquals(0, testPersons.size());
    }

    @Test
    void close() {
        // TODO: (V) NotImplemented
        int id = nextInt();
        String name = randomAlphabetic(7);
        when( departmentDao.findById( anyInt() ) )
                .thenReturn(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            departmentService.update(id, new DepartmentRequest().setName(name));
        });
        String[] msgPart = exception.getMessage().split(String.valueOf(id));
        assertEquals(2, msgPart.length);
    }

}
