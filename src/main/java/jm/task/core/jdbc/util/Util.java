package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class Util {

    public static String dbUrl = "jdbc:mysql://192.168.89.21:3306/firstdb";
    public static String dbUsername = "root";
    public static String dbPassword = "root";
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.URL, dbUrl);
                settings.put(Environment.USER, dbUsername);
                settings.put(Environment.PASS, dbPassword);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");


                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("conn established");
            } catch (JDBCConnectionException e) {
                e.printStackTrace();
                sessionFactory.close();
                System.out.println("not connected");
            }
        }
        return sessionFactory;
    }

    public static void setRollback (Transaction tx) {
        try {
            if (tx.getStatus().canRollback()) {
                tx.rollback();
            }
        } catch (HibernateException e) {
            System.out.println("Transaction error");
        }
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

