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
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(id INT NOT NULL, " +
                "name VARCHAR(20) NOT NULL, " +
                "lastName VARCHAR(20) NOT NULL, " +
                "age TINYINT NOT NULL)";

        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "DROP TABLE IF EXISTS user";
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
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
        Transaction transaction = session.beginTransaction();
        User tmp = new User();
        tmp.setId(id);
        session.delete(tmp);
        transaction.commit();
        session.close();
    }

    @Override
    public List getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<User> user = session.createQuery("from User ").list();
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
        session.close();
    }
}
