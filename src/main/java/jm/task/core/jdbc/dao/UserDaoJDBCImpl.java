package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        if(statement.executeUpdate("CHECK TABLE USER") != -1) {
            statement.executeUpdate("CREATE TABLE USER " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "NAME VARCHAR(20) not null, " +
                    "LASTNAME VARCHAR(20) not null, " +
                    "AGE INT not null)");
        }
        connection.close();
        statement.close();
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        if(statement.executeUpdate("CHECK TABLE USER") == -1) {
            statement.executeUpdate("DROP TABLE USER");
        }
        connection.close();
        statement.close();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getConnection();
        String addUser = "INSERT INTO USER (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(addUser);
        pstmt.setString(1, name);
        pstmt.setString(2, lastName);
        pstmt.setInt(3, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
        pstmt.executeUpdate();
        connection.close();
    }

    public void removeUserById(long id) throws SQLException{
        Connection connection = Util.getConnection();
        String idStr = Long.toString(id);
        String removeUser = "DELETE FROM USER WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(removeUser);
        pstmt.setString(1, idStr);
        pstmt.executeUpdate();
        connection.close();
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> user = new ArrayList<>();
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM USER;");
        while(rs.next()) {
            User us = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
            user.add(us);
        }
        connection.close();
        statement.close();
        return user;
    }

    public void cleanUsersTable()  throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE TABLE USER");
        connection.close();
        statement.close();
    }
}
