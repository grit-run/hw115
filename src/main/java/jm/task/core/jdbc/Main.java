package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
      public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Tim", "Sad", (byte) 45);
        userService.saveUser("Robby", "Good", (byte) 32);
        userService.saveUser("Evil", "Dead", (byte) 70);
        userService.saveUser("Terry", "Smith", (byte) 23);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
