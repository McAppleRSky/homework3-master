package ru.digitalhabits.homework3.service;

import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;
import ru.digitalhabits.homework3.model.PersonFullResponse;
import ru.digitalhabits.homework3.model.PersonShortResponse;

import java.util.ArrayList;
import java.util.List;

public class ResponseHelper {
    public static DepartmentShortResponse buildDepartmentShortResponse(Department department) {
        DepartmentShortResponse shortResponse = new DepartmentShortResponse();
        shortResponse.setId(department.getId());
        shortResponse.setName(department.getName());
        return shortResponse;
    }

    public static DepartmentFullResponse buildDepartmentFullResponse(Department department) {
        DepartmentFullResponse fullResponse = new DepartmentFullResponse();
        fullResponse.setId(department.getId());
        fullResponse.setName(department.getName());
        fullResponse.setClosed(department.getClosed());

        List<PersonShortResponse> personShortResponses = new ArrayList<>();
        for (Person person : department.getPersons()) {
            personShortResponses.add( buildPersonShortResponse(person) );
        }
        fullResponse.setPersons( personShortResponses );
        return fullResponse;
    }

    public static PersonShortResponse buildPersonShortResponse(Person person) {
        PersonShortResponse shortResponse = new PersonShortResponse();
        shortResponse.setId(person.getId());
        shortResponse.setFullName(person.getLastName()
                + " " + person.getFirstName()
                + " " + person.getMiddleName()
        );
        return null;
    }

    public static PersonFullResponse buildPersonFullResponse(Person person) {
        PersonFullResponse fullResponse = new PersonFullResponse();
        fullResponse.setId(person.getId());
        fullResponse.setFullName(person.getLastName()
                + " " + person.getFirstName()
                + " " + person.getMiddleName()
        );
        fullResponse.setAge(person.getAge());
        fullResponse.setDepartment(
                buildDepartmentShortResponse(person.getDepartment())
        );
        return fullResponse;
    }
}
