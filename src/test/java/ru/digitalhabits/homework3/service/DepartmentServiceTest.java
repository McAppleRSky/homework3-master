package ru.digitalhabits.homework3.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.DepartmentDaoImpl;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
        //(classes = DepartmentServiceTest.DepartmentServiceConfiguration.class)
class DepartmentServiceTest {
    // Spy
    private static final int COUNT = 2;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    void findAll() {
        // TODO: NotImplemented
        // Spy
        when(departmentDao.findAll())
                .thenReturn( range(0, COUNT).mapToObj(i -> buildDepartment()).collect(toList()) );

        final List<DepartmentShortResponse> departments = departmentService.findAll();
        assertEquals(COUNT, departments.size());
    }

    @Test
    void findById() {
        // TODO: NotImplemented
    }

    @Test
    void create() {
        // Spy
        // TODO: NotImplemented
        final DepartmentRequest request = buildCreateDepartmentRequest();
        //final Department department = Department.with(request.getName());

        //doNothing().when( departmentDao ).create( any(Department.class) );

        int createdId = departmentService.create(request);

//        ResponseHelper.buildDepartmentShortResponse(Department.with())
        assertEquals(0, createdId);
    }

    @Test
    void update() {
        // TODO: NotImplemented
    }

    @Test
    void delete() {
        // TODO: NotImplemented
    }

    @Test
    void close() {
        // TODO: NotImplemented
    }

    private DepartmentRequest buildCreateDepartmentRequest() {
        DepartmentRequest departmentRequest = new DepartmentRequest();
        departmentRequest.setName("randomAlphabetic(7)");
        return departmentRequest;
    }

    // Spy
    private Department buildDepartment() {
        return Department.with(randomAlphabetic(7));
    }
    //@Configuration
    //@Import(MockRepositoriesConfiguration.class)
    static class DepartmentServiceConfiguration{

        @Bean
        public DepartmentService departmentService(DepartmentDao departmentRepository){
            return new DepartmentServiceImpl(departmentRepository);
        }
    }
}
