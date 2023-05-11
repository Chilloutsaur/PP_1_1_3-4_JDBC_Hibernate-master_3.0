package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

   static private final Connection connection = Util.getConnection();

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists users (id bigint not null primary key auto_increment, " +
                    "name varchar (50) null, " +
                    "lastName varchar(50) null," +
                    " age tinyint null)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("drop table if exists users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static private final String INSERT_NEW = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preState = connection.prepareStatement(INSERT_NEW)) {
            preState.setString(1, name);
            preState.setString(2, lastName);
            preState.setInt(3, age);
            preState.execute();
            System.out.println("User с именем " + name + " Добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preState = connection.prepareStatement("DELETE FROM users WHERE id = (?)")) {
            preState.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                userList.add(new User(rs.getLong("id"), rs.getString("name"), rs.getString("lastName"), rs.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("truncate table users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
