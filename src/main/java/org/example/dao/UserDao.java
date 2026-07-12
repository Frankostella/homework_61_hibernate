package org.example.dao;

import org.example.entity.User;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {
        public void save(User user) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            session.close();

        }

        public User findById(Long id) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            transaction.commit();
            session.close();
            return user;

        }

        public List<User> findAll() {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class)
                    .getResultList();
            transaction.commit();
            session.close();
            return users;

        }

        public void update(User user) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            session.close();

        }

        public void delete(Long id) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }

            transaction.commit();
            session.close();

        }
    }

