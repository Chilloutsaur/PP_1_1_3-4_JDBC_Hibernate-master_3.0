package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl user  = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Dwayne", "The Rock", (byte) 33);
        user.saveUser("John", "Sena", (byte) 35);
        user.saveUser("Phil", "Smith", (byte) 40);
        user.saveUser("Kate", "Jackson", (byte) 35);
        System.out.print(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();

    }
}
