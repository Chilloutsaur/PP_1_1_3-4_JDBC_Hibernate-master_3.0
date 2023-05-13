package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserService user = new UserServiceImpl();
        user.createUsersTable();
        userService.saveUser("Anton", "Chigurh", (byte) 47);
        userService.saveUser("Gannibal", "Lector", (byte) 52);
        userService.saveUser("Hans", "Landa", (byte) 43);
        userService.saveUser("T", "800", (byte) 1);
        System.out.println(user.getAllUsers());
        user.removeUserById(3);
        System.out.println();
        System.out.println(user.getAllUsers());



    }
}
