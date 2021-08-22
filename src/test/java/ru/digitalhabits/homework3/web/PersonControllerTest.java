package ru.digitalhabits.homework3.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;
import ru.digitalhabits.homework3.model.PersonFullResponse;
import ru.digitalhabits.homework3.model.PersonRequest;
import ru.digitalhabits.homework3.model.PersonShortResponse;
import ru.digitalhabits.homework3.service.DepartmentService;
import ru.digitalhabits.homework3.service.PersonService;
import ru.digitalhabits.homework3.service.ResponseHelper;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest(
        webEnvironment=SpringBootTest.WebEnvironment.MOCK,
        classes = PersonControllerTest.PersonControllerConfiguration.class)
class PersonControllerTest {

    //@Autowired private MockMvc mockMvc;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonController personController;

    @Test
    void persons() {
        // TODO: (V~) NotImplemented
        when(personService.findAll())
                .thenReturn(new ArrayList<PersonShortResponse>());
        List<PersonShortResponse> persons = personController.persons();
        assertEquals(ArrayList.class, personController.persons().getClass());
    }

    @Test
    void person() {
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
        when(personService.getById(anyInt()))
                .thenReturn(
                        ResponseHelper.buildPersonFullResponse(
                                Person.with(ints[ID])
                                .setFirstName(name[FIRST])
                                .setLastName(name[LAST])
                                .setMiddleName(name[MIDDLE])
                                .setAge(ints[AGE])
                                .setDepartment(Department.with(randomAlphabetic(7)))
                        )
                );
        PersonFullResponse actualResponse = personController.person(ints[ID]);
        assertEquals(ints[ID], actualResponse.getId());
        assertEquals(name[LAST] + " " + name[FIRST] + " " + name[MIDDLE], actualResponse.getFullName());
        assertEquals(ints[AGE], actualResponse.getAge());
    }

    @Test
    void createPerson() {
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
        PersonRequest personRequest = new PersonRequest()
                .setFirstName(name[FIRST])
                .setLastName(name[LAST])
                .setMiddleName(name[MIDDLE])
                .setAge(ints[AGE]);
        when(personService.create(any(PersonRequest.class)))
                .thenReturn(ints[ID]);
        ResponseEntity<Void> actualPersonResponse = personController.createPerson(personRequest);
        assertEquals(201, actualPersonResponse.getStatusCodeValue());
        assertEquals("Created", actualPersonResponse.getStatusCode().getReasonPhrase());
    }

    @Test
    void updatePerson() {
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
        PersonRequest personRequest = new PersonRequest()
                .setFirstName(name[FIRST])
                .setLastName(name[LAST])
                .setMiddleName(name[MIDDLE])
                .setAge(ints[AGE]);
        PersonFullResponse personResponse = ResponseHelper.buildPersonFullResponse(
                Person.with(ints[ID])
                        .setFirstName(name[FIRST])
                        .setLastName(name[LAST])
                        .setMiddleName(name[MIDDLE])
                        .setAge(ints[AGE])
                        .setDepartment(
                                Department.with(randomAlphabetic(7))
                        )
                );
        when(personService.update(anyInt(), any(PersonRequest.class)))
                .thenReturn(personResponse);
        PersonFullResponse actualPersonResponse = personController.updatePerson(ints[ID], personRequest);
        assertEquals(ints[ID], actualPersonResponse.getId());
        assertEquals(name[LAST] + " " + name[FIRST] + " " + name[MIDDLE], actualPersonResponse.getFullName());
        assertEquals(ints[AGE], actualPersonResponse.getAge());
    }

    @Test
    void deletePerson() {
        // TODO: (V) NotImplemented
        int id = nextInt();
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(id, arg0);
            return null;
        }).when(personService).delete(anyInt());
        personController.deletePerson(id);
    }

    @Configuration
    static class PersonControllerConfiguration{
        @Bean
        PersonService personService(){
            return mock(PersonService.class);
        }
        @Bean
        PersonController personController(){
            return new PersonController( personService() );
        }
    }
}
