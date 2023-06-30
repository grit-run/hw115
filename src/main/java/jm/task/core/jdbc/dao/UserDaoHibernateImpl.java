package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
//    private static final SessionFactory sf = (SessionFactory) Util.getSessionFactory().openSession();


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createNativeQuery("CREATE TABLE users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age TINYINT)");
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            //Ignore
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query addUserQuery = session.createNativeQuery("INSERT INTO users(name, lastName, age) VALUES(?,?,?)");
            addUserQuery.setParameter(1, name);
            addUserQuery.setParameter(2, lastName);
            addUserQuery.setParameter(3, age);
//            addUserQuery.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createNativeQuery("DELETE FROM users WHERE id = :id").setParameter("id", id);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List users = new ArrayList<User>();
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            users = session.createNativeQuery("SELECT * FROM users", User.class).getResultList();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
