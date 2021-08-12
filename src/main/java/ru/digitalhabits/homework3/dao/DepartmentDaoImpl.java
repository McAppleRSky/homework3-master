package ru.digitalhabits.homework3.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;
import ru.digitalhabits.homework3.domain.Department;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DepartmentDaoImpl
        implements DepartmentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Nullable
    @Override
    public Department findById(@Nonnull Integer id) {
        // TODO: NotImplemented (V)
        return entityManager.find(Department.class, id);
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public List<Department> findAll() {
        // TODO: NotImplemented (V)
        return entityManager.createQuery("select d from Department d", Department.class).getResultList();
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public Department update(@Nonnull Department department) {
        // TODO: NotImplemented (V)
        return entityManager.merge(department);
        //throw new NotImplementedException();
    }

    @Nullable
    @Override
    public Department delete(@Nonnull Integer id) {
        // TODO: NotImplemented (V)
        Department deletedDepartment = entityManager.find(Department.class, id);
        entityManager.remove(deletedDepartment);
        return deletedDepartment;
        //throw new NotImplementedException();
    }

    @Override
    public void create(@Nonnull Department newDepartment) {
        entityManager.persist(newDepartment);
    }

    /*@Override
    public void emplying(Department newDepartment) {
    }*/

}
