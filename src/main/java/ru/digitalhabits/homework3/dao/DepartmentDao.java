package ru.digitalhabits.homework3.dao;

import org.springframework.data.repository.NoRepositoryBean;
import ru.digitalhabits.homework3.domain.Department;

import javax.annotation.Nonnull;

@NoRepositoryBean
public interface DepartmentDao
        extends CrudOperations<Department, Integer> {
    void create(Department newDepartment);
    /*void emplying(Department newDepartment);*/
}
