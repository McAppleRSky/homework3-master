package ru.digitalhabits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.dao.PersonDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.PersonFullResponse;
import ru.digitalhabits.homework3.model.PersonRequest;
import ru.digitalhabits.homework3.model.PersonShortResponse;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl
        implements PersonService {

    private final PersonDao personDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<PersonShortResponse> findAll() {
        // TODO: (V) NotImplemented: получение информации о всех людях во всех отделах
        return personDao.findAll()
                .stream()
                .map(ResponseHelper::buildPersonShortResponse)
                .collect(toList());
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public PersonFullResponse getById(int id) {
        // TODO: (V) NotImplemented: получение информации о человеке. Если не найдено, отдавать 404:NotFound
        return ofNullable(personDao.findById(id))
                .map(ResponseHelper::buildPersonFullResponse)
                .orElseThrow(() -> new EntityNotFoundException("Person '" + id + "' not found"));
        //throw new NotImplementedException();
    }

    @Override
    @Transactional
    public int create(@Nonnull PersonRequest request) {
        // TODO: (V) NotImplemented: создание новой записи о человеке
        Department absentedDepartment = null;
        Person creatingPerson = Person.with(absentedDepartment, request.getFirstName(), request.getLastName(), request.getMiddleName(), request.getAge());
        Person createdPerson = personDao.save(creatingPerson);
        return createdPerson.getId();
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    @Transactional
    public PersonFullResponse update(int id, @Nonnull PersonRequest request) {
        // TODO: (V) NotImplemented: обновление информации о человеке. Если не найдено, отдавать 404:NotFound
        Person updatingPerson = personDao.findById(id);
        if (updatingPerson==null){
            throw new EntityNotFoundException("Person '" + id + "' not found");
        } else {
            updatingPerson.setFirstName(request.getFirstName());
            updatingPerson.setLastName(request.getLastName());
            updatingPerson.setMiddleName(request.getMiddleName());
            personDao.update(updatingPerson);
        }
        return getById(id);
        //throw new NotImplementedException();
    }

    @Override
    @Transactional
    public void delete(int id) {
        // TODO: (V) NotImplemented: удаление информации о человеке и удаление его из отдела. Если не найдено, ничего не делать
        Person deletingPerson = personDao.findById(id);
        if (deletingPerson!=null){
            personDao.delete(id);
        }
        //throw new NotImplementedException();
    }


    @Override
    @Transactional
    public void addPersonToDepartment(int departmentId, int personId) {
    // TODO: (V) NotImplemented: добавление нового человека в департамент.
    //  Если не найден человек или департамент, отдавать 404:NotFound.
    //  Если департамент закрыт, то отдавать 409:Conflict
        Department toDepartment = departmentDao.findById(departmentId);
        if(toDepartment==null){
            throw new EntityNotFoundException("Department '" + departmentId + "' not found");
        } else {
            if (toDepartment.getClosed()) {
                throw new IllegalStateException("Department '" + departmentId + "' is closed");
            } else {
                Person addingPerson = personDao.findById(personId);
                if (addingPerson==null){
                    throw new EntityNotFoundException("Person '" + personId + "' not found");
                } else {
                    List<Person> persons = toDepartment.getPersons();
                    persons.add(addingPerson);
                    toDepartment.setPersons(persons);
                    departmentDao.update(toDepartment);
                }
            }
        }
    }

    @Override
    @Transactional
    public void removePersonFromDepartment(int departmentId, int personId) {
    // TODO: (V) NotImplemented: удаление человека из департамента.
    //  Если департамент не найден, отдавать 404:NotFound, если не найден человек в департаменте, то ничего не делать
        Department fromDepartment = departmentDao.findById(departmentId);
        if(fromDepartment==null){
            throw new EntityNotFoundException("Department '" + departmentId + "' not found");
        } else {
            Person removingPerson = personDao.findById(personId);
            List<Person> persons = fromDepartment.getPersons();
            persons.remove(removingPerson);
            fromDepartment.setPersons(persons);
            departmentDao.update(fromDepartment);
        }
        // throw new NotImplementedException();
    }

    @Override
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

}
