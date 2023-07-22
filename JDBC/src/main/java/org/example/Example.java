// packages are basically c# namespaces
package org.example;

// using a package
import org.example.exts.DbManipulations;
import org.example.exts.StringExts;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Example {
    public static void main(String[] args) {
        // url is the JDBC connection string
        // (syntax: jdbc:provider://path_to_db{/database_name} (database name is optional)
        // final is a constant
        final String url = "jdbc:mariadb://localhost:3306",
                user = "root", password = "123456";

        // block try-catch also works as C# "using" statement.
        // connection to the database SERVER
        try (Connection serverConn = DriverManager.getConnection(url, user, password)) {
            String dbName = "java_salo";
            DbManipulations.ensureDbExists(serverConn, dbName);

            // connection to the database
            try (Connection dbConn = DriverManager.getConnection(url + "/" + dbName, user, password)) {
                UserDbWorker.ensureUsersTable(dbConn);
                Example.menu(dbConn);
            }
        } catch (Exception ex) {
            // writes a line to the console and moves to the next line
//            System.out.println(StringExts.ANSI_RED +
//                    "Something went wrong: " + ex.getMessage() +
//                    StringExts.ANSI_RESET);

                StringExts.printRed("Something went wrong: " + ex.getMessage());
        }
    }

    public static void menu(Connection connection) throws SQLException {
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
                        boolean createResult = UserDbWorker.Create(connection, username, email);

                        if (createResult) {
                            StringExts.printGreen("User created successfully.");
                        } else {
                            StringExts.printRed("Failed to create user.");
                        }
                    }
                    case 2 -> {
                        String allUsers = UserDbWorker.Read(connection);
                        StringExts.printGreen(allUsers);
                    }
                    case 3 -> {
                        StringExts.printPurple("Enter user ID: ");
                        int userId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        String userById = UserDbWorker.Read(connection, userId);
                        StringExts.printGreen(userById);
                    }
                    case 4 -> {
                        StringExts.printPurple("Enter user ID: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        StringExts.printPurple("Enter new username: ");
                        String newUsername = scanner.nextLine();
                        StringExts.printPurple("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        boolean updateResult = UserDbWorker.Update(connection, updateId, newUsername, newEmail);

                        if (updateResult) {
                            StringExts.printGreen("User updated successfully.");
                        } else {
                            StringExts.printRed("Failed to update user.");
                        }
                    }
                    case 5 -> {
                        StringExts.printPurple("Enter user ID: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
                        boolean deleteResult = UserDbWorker.Delete(connection, deleteId);

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