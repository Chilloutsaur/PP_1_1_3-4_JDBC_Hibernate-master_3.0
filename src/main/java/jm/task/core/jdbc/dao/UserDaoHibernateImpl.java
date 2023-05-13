package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    private static final SessionFactory sessionFactory = Util.getSessionFactory();


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
        } catch (RollbackException e) {
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (RollbackException e) {
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (RollbackException e) {
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));

            session.getTransaction().commit(); // <- терминальная операция, запускает.
        } catch (RollbackException e) {
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        List<User> users = new ArrayList<>();
        try {
            session.beginTransaction();
            TypedQuery<User> query = session.createQuery("from User ", User.class); // Работа с дженериками, указывание какой класс получить.
            users = query.getResultList();
            session.getTransaction().commit();
        } catch (RollbackException e) {
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (RollbackException e) {
            try {
                if (sessionFactory.getCurrentSession().getTransaction() != null) {
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (HibernateException he) {
                he.printStackTrace();
            }
        }
    }
}
