package ru.digitalhabits.homework3.dao;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.digitalhabits.homework3.domain.Department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "ru.digitalhabits.homework3")
class DepartmentDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    void createWhenEntityManagerIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new TestEntityManager(null))
                .withMessageContaining("EntityManagerFactory must not be null");
    }

    @Test
    void findById() {
        // TODO: (V) NotImplemented
        String name = randomAlphabetic(7);
        int id = nextInt();
        String querySrc = "insert into department (id, name) values (:id, :name);";
        entityManager.getEntityManager().createNativeQuery(querySrc)
                .setParameter("id", id)
                .setParameter("name", name)
                .executeUpdate();
        Department departmentActual = departmentDao.findById(id);
        assertNotNull(departmentActual);
        assertEquals(id, departmentActual.getId());
        assertEquals(name, departmentActual.getName());
    }

    @Test
    void findAll() {
        // TODO: (V) NotImplemented
        Map<Integer, String> testValues = new HashMap<>();
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);
        for (int i = 0; i < count; i++) {
            testValues.put(nextInt(), randomAlphabetic(7));
        }
        String querySrc = "insert into department (id, name) values (:id, :name);";
        for (Map.Entry<Integer, String> entry : testValues.entrySet()) {
            entityManager.getEntityManager().createNativeQuery(querySrc)
                    .setParameter("id", entry.getKey())
                    .setParameter("name", entry.getValue())
                    .executeUpdate();
        }
        List<Department> departments = departmentDao.findAll();
        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        assertEquals(count, departments.size());
        for (Department departmentCurrent : departments) {
            String storedName = testValues.remove(departmentCurrent.getId());
            assertEquals(departmentCurrent.getName(), storedName);
        }
        assertTrue(testValues.isEmpty());
    }

    @Test
    void update() {
        // TODO: (V) NotImplemented
        String nameBeforeUpdateName = randomAlphabetic(7);
        int id = nextInt();
        String querySrc = "insert into department (id, name) values (:id, :name);";
        entityManager.getEntityManager().createNativeQuery(querySrc)
                .setParameter("id", id)
                .setParameter("name", nameBeforeUpdateName)
                .executeUpdate();
        String nameAfterUpdate = randomAlphabetic(7);
        Department departmentUpdating = Department.with(nameAfterUpdate);
        departmentUpdating.setId(id);
        Department departmentAfterUpdate = departmentDao.update(departmentUpdating);
        assertNotNull(departmentAfterUpdate);
        assertNotEquals(nameBeforeUpdateName, departmentAfterUpdate.getName());
        assertEquals(nameAfterUpdate, departmentAfterUpdate.getName());
    }

    @Test
    void delete() {
        // TODO: (V) NotImplemented
        Map<Integer, String> testValues = new HashMap<>();
        int min = 2, max = 9;
        Random random = new Random();
        int count = min + random.nextInt(max-min);
        int targetI = min + random.nextInt(count-min) - 1;
        Pair departmentDeleting = null;
        for (int i = 0; i < count; i++) {
            int randimId = nextInt();
            testValues.put(randimId, randomAlphabetic(7));
            if (i==targetI) {
                departmentDeleting = Pair.of(randimId, testValues.get(randimId));
            }
        }
        String querySrc = "insert into department (id, name) values (:id, :name);";
        for (Map.Entry<Integer, String> entry : testValues.entrySet()) {
            entityManager.getEntityManager().createNativeQuery(querySrc)
                    .setParameter("id", entry.getKey())
                    .setParameter("name", entry.getValue())
                    .executeUpdate();
        }
        querySrc = "select * from department;";
        assertEquals(count, entityManager.getEntityManager().createNativeQuery(querySrc).getResultList().size());
        Department departmentDeleted = departmentDao.delete((Integer) departmentDeleting.getKey());
        assertEquals(departmentDeleting.getValue().toString(), departmentDeleted.getName());
        assertEquals(count-1, entityManager.getEntityManager().createNativeQuery(querySrc).getResultList().size());
    }
}
