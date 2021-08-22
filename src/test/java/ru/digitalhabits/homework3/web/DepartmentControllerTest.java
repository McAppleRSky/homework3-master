package ru.digitalhabits.homework3.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;
import ru.digitalhabits.homework3.service.DepartmentService;
import ru.digitalhabits.homework3.service.PersonService;
import ru.digitalhabits.homework3.service.ResponseHelper;

import java.util.ArrayList;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(
        webEnvironment=SpringBootTest.WebEnvironment.MOCK,
        classes = DepartmentControllerTest.DepartmentControllerConfiguration.class)
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private PersonService personService;
    @Autowired
    private DepartmentController departmentController;

    @Test
    void departments() {
        // TODO: (V~) NotImplemented
        when(departmentService.findAll())
                .thenReturn(new ArrayList<DepartmentShortResponse>());
        assertEquals(ArrayList.class, departmentController.departments().getClass());
    }

    @Test
    void department() {
        // TODO: (V~) NotImplemented
        int id = nextInt();
        String name = randomAlphabetic(7);
        when(departmentService.getById(anyInt()))
                .thenReturn(ResponseHelper.buildDepartmentFullResponse(
                        Department.with(name)
                                .setId(id)
                                .setClosed(false)
                                .setPersons(new ArrayList<Person>())));
        DepartmentFullResponse actualResponse = departmentController.department(id);
        assertEquals(name, actualResponse.getName());
        assertEquals(id, actualResponse.getId());
        assertEquals(false, actualResponse.isClosed());
    }

    @Test
    void createDepartment() {
        // TODO: (V~) NotImplemented
        int id = nextInt();
        String name = randomAlphabetic(7);
        DepartmentRequest request = new DepartmentRequest().setName(name);
        when(departmentService.create(any(DepartmentRequest.class)))
                .thenReturn(id);
        ResponseEntity<Void> actualDepartmentResponse = departmentController.createDepartment(request);
        assertEquals(201, actualDepartmentResponse.getStatusCodeValue());
        assertEquals("Created", actualDepartmentResponse.getStatusCode().getReasonPhrase());
    }

    @Test
    void updateDepartment() {
        // TODO: (V) NotImplemented
        int id = nextInt();
        String name = randomAlphabetic(7);
        DepartmentRequest request = new DepartmentRequest().setName(name);
        when(departmentService.update(anyInt(), any(DepartmentRequest.class)))
                .thenReturn(ResponseHelper.buildDepartmentFullResponse(
                        Department.with(name)
                                .setId(id)
                                .setClosed(false)
                                .setPersons(new ArrayList<Person>())));
        DepartmentFullResponse actualResponse = departmentController.updateDepartment(id, request);
        assertEquals(name, actualResponse.getName());
        assertEquals(id, actualResponse.getId());
        assertEquals(false, actualResponse.isClosed());
    }

    @Test
    void deleteDepartment() {
        // TODO: (V) NotImplemented
        int id = nextInt();
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(id, arg0);
            return null;
        }).when(departmentService).delete(anyInt());
        departmentController.deleteDepartment(id);
    }

    @Test
    void addPersonToDepartment() {
        // TODO: (V) NotImplemented
        int departmentId = nextInt(), personId = nextInt();
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            assertEquals(departmentId, arg0);
            assertEquals(personId, arg1);
            return null;
        }).when(personService).addPersonToDepartment(anyInt(), anyInt());
        departmentController.addPersonToDepartment(departmentId, personId);
    }

    @Test
    void removePersonToDepartment() {
        // TODO: (V) NotImplemented
        int departmentId = nextInt(), personId = nextInt();
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            assertEquals(departmentId, arg0);
            assertEquals(personId, arg1);
            return null;
        }).when(personService).removePersonFromDepartment(anyInt(), anyInt());
        departmentController.removePersonFromDepartment(departmentId, personId);
    }

    @Test
    void closeDepartment() {
        // TODO: (v) NotImplemented
        int departmentId = nextInt();
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(departmentId, arg0);
            return null;
        }).when(departmentService).close(anyInt());
        departmentController.closeDepartment(departmentId);
    }

    @Configuration
    //@Import(MockServiceConfiguration.class)
    static class DepartmentControllerConfiguration{
        @Bean
        DepartmentService departmentService(){
            return mock(DepartmentService.class);
        }
        @Bean
        PersonService personService(){
            return mock(PersonService.class);
        }
        @Bean
        DepartmentController departmentController(){
            return new DepartmentController( departmentService(), personService() );
        }
    }

}