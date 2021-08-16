package ru.digitalhabits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabits.homework3.dao.DepartmentDao;
import ru.digitalhabits.homework3.domain.Department;
import ru.digitalhabits.homework3.domain.Person;
import ru.digitalhabits.homework3.model.DepartmentFullResponse;
import ru.digitalhabits.homework3.model.DepartmentRequest;
import ru.digitalhabits.homework3.model.DepartmentShortResponse;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
        implements DepartmentService {

    // spy:
    private final DepartmentDao departmentDao;

    @Autowired
    private PersonService personService;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentShortResponse> findAll() {
        // TODO: (V) NotImplemented: получение краткой информации о всех департаментах
        // spy:
        return departmentDao.findAll()
                .stream()
                .map(ResponseHelper::buildDepartmentShortResponse)
                .collect(toList());
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public DepartmentFullResponse getById(int id) {
        // TODO: (V~) NotImplemented: получение подробной информации о департаменте и краткой информации о людях в нем.
        //  Если не найдено, отдавать 404:NotFound
        return ofNullable(departmentDao.findById(id))
                .map(ResponseHelper::buildDepartmentFullResponse)
                .orElseThrow(() -> new EntityNotFoundException("Department '" + id + "' not found"));
        //throw new NotImplementedException();
    }

    @Override
    @Transactional
    public int create(@Nonnull DepartmentRequest request) {
        // TODO: (V) NotImplemented: создание нового департамента
        Department newDepartment = Department.with(request.getName());
        //departmentDao.create(newDepartment);
        return newDepartment.getId();
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    @Transactional
    public DepartmentFullResponse update(int id, @Nonnull DepartmentRequest request) {
        // TODO: (V) NotImplemented: обновление данных о департаменте. Если не найдено, отдавать 404:NotFound
        Department updatingDepartment = departmentDao.findById(id);
        if (updatingDepartment==null){
            throw new EntityNotFoundException("Department '" + id + "' not found");
        } else {
            updatingDepartment.setName(request.getName());
            departmentDao.update(updatingDepartment);
        }
        return getById(id);
        //throw new NotImplementedException();
    }

    @Override
    @Transactional
    public void delete(int id) {
        // TODO: (V) NotImplemented: удаление всех людей из департамента и удаление самого департамента.
        //  Если не найдено, то ничего не делать
        Department deletingDepartment = departmentDao.findById(id);
        if (deletingDepartment!=null){
            for (Person deletingPerson : deletingDepartment.getPersons()) {
                personService.removePersonFromDepartment(id, deletingPerson.getId());
            }
            departmentDao.delete(id);
        }
        //throw new NotImplementedException();
    }

    @Override
    @Transactional
    public void close(int id) {
        // TODO: (V) NotImplemented: удаление всех людей из департамента и установка отметки на департаменте,
        //  что он закрыт для добавления новых людей. Если не найдено, отдавать 404:NotFound
        Department closingDepartment = departmentDao.findById(id);
        if (closingDepartment==null){
            throw new EntityNotFoundException("Department '" + id + "' not found");
        } else {
            for (Person deletingPerson : closingDepartment.getPersons()) {
                personService.removePersonFromDepartment(id, deletingPerson.getId());
            }
        }
        closingDepartment.setClosed(true);
        departmentDao.update(closingDepartment);
        //throw new NotImplementedException();
    }
}
