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
        // auto close session object
            // start the transaction

            // save student object
        session.save(user);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
            // commit transction
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        System.out.println("Delete User!");
        Transaction tx1 = session.beginTransaction();
        session.delete(session.get(User.class, id));
        tx1.commit();
//        session.close();
    }

    @Override
    public List getAllUsers() throws SQLException {
        Session session = Util.getSessionFactory().openSession();
//        Session session = Util.getSessionFactory().openSession();
//        Transaction tx1 = session.beginTransaction();
////        List<User> user = session.createQuery("from User ").list();
////        for (User us: user) {
////            System.out.println(us);
////        }
////
//        List users = session.createCriteria(User.class).list();
////        System.out.println(user);
//        tx1.commit();
//        session.close();
        return Util.getSessionFactory().openSession().createCriteria(User.class).list();
    }

    @Override
    public void cleanUsersTable() {

    }
}
