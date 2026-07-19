package org.example.dao;

import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
class UserDaoTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16");

    @BeforeAll
    static void beforeAll() {

        SessionFactory factory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.url", postgres.getJdbcUrl())
                .setProperty("hibernate.connection.username", postgres.getUsername())
                .setProperty("hibernate.connection.password", postgres.getPassword())
                .buildSessionFactory();

        HibernateUtil.setSessionFactory(factory);
    }

    @Test
    void saveShouldSaveUser() {

        UserDao userDao = new UserDao();

        User user = new User();
        user.setName("Kate");
        user.setEmail("kate@test.com");
        user.setAge(25);

        userDao.save(user);

        assertNotNull(user.getId());
    }

    @Test
    void findByIdShouldReturnUser() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setName("Kate");
        user.setEmail("kate@test.com");
        user.setAge(25);
        userDao.save(user);

        User found = userDao.findById(user.getId());

        assertNotNull(found);
        assertEquals("Kate", found.getName());
        assertEquals("kate@test.com", found.getEmail());
    }

    @Test
    void findAllShouldReturnUsers() {
        UserDao userDao = new UserDao();
        User user1 = new User();
        user1.setName("Kate");
        user1.setEmail("kate2@test.com");
        user1.setAge(25);

        User user2 = new User();
        user2.setName("Ron");
        user2.setEmail("ron@test.com");
        user2.setAge(23);

        userDao.save(user1);
        userDao.save(user2);

        List<User> users = userDao.findAll();
        assertEquals(2, users.size());

    }

    @Test
    void updateShouldUpdateUser() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setName("Kate");
        user.setEmail("kate3@test.com");
        user.setAge(25);
        userDao.save(user);

        user.setName("Ron");
        user.setAge(23);
        userDao.update(user);
        User updated = userDao.findById(user.getId());
        assertEquals("Ron", updated.getName());
        assertEquals(23, updated.getAge().intValue());

    }

    @Test
    void deleteShouldDeleteUser() {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setName("Kate");
        user.setEmail("kate4@test.com");
        user.setAge(25);
        userDao.save(user);

        Long id = user.getId();
        userDao.delete(id);
        User deleted = userDao.findById(id);
        assertNull(deleted);
    }

    @BeforeEach
    void clearDataBase() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeMutationQuery(
                "TRUNCATE TABLE users RESTART IDENTITY CASCADE"
        ).executeUpdate();
        transaction.commit();
        session.close();
    }
}