package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class UserDaoHibernateImpl implements UserDao {
    private static byte idd = 1;
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() throws SQLException {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(20) NOT NULL, lastName VARCHAR(20) NOT NULL, " +
                "age TINYINT NOT NULL)";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        System.out.println("Table is created!");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() throws SQLException {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS user";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        System.out.println("Add User!");
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        user.setId((long) idd);
        idd++;
        session.save(user);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        System.out.println("Delete User!");
        Transaction transaction = session.beginTransaction();
        session.delete(session.get(User.class, id));
        transaction.commit();
        session.close();
    }

    @Override
    public List getAllUsers() throws SQLException {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> user = session.createQuery("from User ").list();
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public void cleanUsersTable() {

    }
}
