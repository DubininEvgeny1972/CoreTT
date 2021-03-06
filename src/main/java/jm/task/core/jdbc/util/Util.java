package jm.task.core.jdbc.util;
import java.sql.*;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/User?useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/User?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

    public static SessionFactory sessionFactoryNul() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        return null;
    }

    public static Connection getConnection(){
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException throwables) {
            System.out.println("???????????????????? ???? ??????????????");
            throwables.printStackTrace();
        }
        return connection;
    }
}