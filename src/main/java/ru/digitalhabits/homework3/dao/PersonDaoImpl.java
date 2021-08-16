package ru.digitalhabits.homework3.dao;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.digitalhabits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class PersonDaoImpl
        implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Nullable
    @Override
    public Person findById(@Nonnull Integer id) {
        // TODO: (V) NotImplemented
        return entityManager.find(Person.class, id);
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public List<Person> findAll() {
        // TODO: (V) NotImplemented
        return entityManager.createQuery("select p from Person p").getResultList();
        //throw new NotImplementedException();
    }

    @Nonnull
    @Override
    public Person update(@Nonnull Person person) {
        // TODO: (V) NotImplemented
        return entityManager.merge(person);
        //throw new NotImplementedException();
    }

    @Nullable
    @Override
    public Person delete(@Nonnull Integer id) {
        // TODO: (V) NotImplemented
        Person deletedPerson = entityManager.find(Person.class, id);
        entityManager.remove(deletedPerson);
        return deletedPerson;
        //throw new NotImplementedException();
    }

    @Override
    public void create(Person newPerson) {
        entityManager.persist(newPerson);
    }
}
