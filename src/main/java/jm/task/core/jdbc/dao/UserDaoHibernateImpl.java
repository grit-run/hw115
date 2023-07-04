package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final String hqlCreateTable = "CREATE TABLE IF NOT EXISTS users" +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL , lastname VARCHAR(255) NOT NULL , " +
            "age TINYINT(255) NOT NULL)";
    private static final String hqlDrop = "DROP TABLE IF EXISTS users";
    private static final String hqlDeleteAll = "delete from users";

    private static final String hqlSelectAll = "FROM User";

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            try {
                session.createSQLQuery(hqlCreateTable).addEntity(User.class).executeUpdate();
                tx.commit();
            } catch (HibernateException e) {
                Util.setRollback(tx);
            }
        } catch (Exception e) {
            // Ignore
        }
    }

    @Override
    public void dropUsersTable() {
        dropTableOrClearRows(hqlDrop);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                tx.commit();
            } catch (HibernateException e) {
                Util.setRollback(tx);
            }
        } catch (Exception e) {
            // Ignore
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                session.delete(user);
                tx.commit();
            } catch (HibernateException e) {
                Util.setRollback(tx);
            }
        } catch (Exception e) {
            // Ignore
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery(hqlSelectAll, User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        dropTableOrClearRows(hqlDeleteAll);
    }

    public static void dropTableOrClearRows(String hqlQuery) {
        try (Session session = Util.getSessionFactory().openSession()) {
            final Transaction tx = session.beginTransaction();
            try {
                session.createSQLQuery(hqlQuery).executeUpdate();
                tx.commit();
            } catch (HibernateException e) {
                Util.setRollback(tx);
            }
        } catch (Exception e) {
            // Ignore
        }
    }
}




