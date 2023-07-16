package org.example;

import org.example.exts.StringExts;
import org.example.models.User;
import org.example.orm.DbUtil;
import org.example.orm.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (DbUtil util = new DbUtil();
                UserDAO dao = new UserDAO(util.getSessionFactory())) {
            menu(dao);
        } catch (Exception ex) {
            StringExts.printRed("Something went wrong: " + ex.getMessage());
        }
    }

    public static void menu(UserDAO DAO) throws SQLException {
        // Scanner reads data from the specified stream
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        do {
            try {
                StringExts.printBlue(choices);

                StringExts.printPurple("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> {
                        StringExts.printPurple("Enter username: ");
                        String username = scanner.nextLine();
                        StringExts.printPurple("Enter email: ");
                        String email = scanner.nextLine();
                        boolean createResult = DAO.create(username, email);

                        if (createResult) {
                            StringExts.printGreen("User created successfully.");
                        } else {
                            StringExts.printRed("Failed to create user.");
                        }
                    }
                    case 2 -> {
                        for (User u: DAO.readAll()) {
                            StringExts.printGreen(u.toString());
                        }
                    }
                    case 3 -> {
                        StringExts.printPurple("Enter user ID: ");
                        Long userId = scanner.nextLong();
                        scanner.nextLine(); // Consume the newline character
                        User userById = DAO.readById(userId);
                        if (userById == null) StringExts.printRed("User is not found");
                        else StringExts.printGreen(userById.toString());
                    }
                    case 4 -> {
                        StringExts.printPurple("Enter user ID: ");
                        Long updateId = scanner.nextLong();
                        scanner.nextLine(); // Consume the newline character
                        StringExts.printPurple("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        StringExts.printPurple("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        boolean updateResult = DAO.update(updateId, newUsername, newEmail);

                        if (updateResult) {
                            StringExts.printGreen("User updated successfully.");
                        } else {
                            StringExts.printRed("Failed to update user.");
                        }
                    }
                    case 5 -> {
                        StringExts.printPurple("Enter user ID: ");
                        Long deleteId = scanner.nextLong();
                        scanner.nextLine(); // Consume the newline character
                        boolean deleteResult = DAO.delete(deleteId);

                        if (deleteResult) {
                            StringExts.printGreen("User deleted successfully.");
                        } else {
                            StringExts.printRed("Failed to delete user.");
                        }
                    }
                    case 0 -> exit = true;
                    default -> StringExts.printYellow("Invalid choice. Please try again.");
                }

                System.out.println();
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                StringExts.printRed("Enter integer next time");
            }
        } while (!exit);
    }

    private static final String choices = new StringBuilder()
            .append("----- User Database Menu -----\n")
            .append("1. Create User\n")
            .append("2. Read All Users\n")
            .append("3. Read User by ID\n")
            .append("4. Update User\n")
            .append("5. Delete User\n")
            .append("0. Exit")
            .toString();
}