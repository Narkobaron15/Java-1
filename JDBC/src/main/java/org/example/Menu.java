package org.example;

import org.example.exts.StringExts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <p><b>A menu class</b></p>
 * <p>Designed specifically for console interactions
 * as an example of standard Java database utilities</p>
 */
public class Menu {
    private static final String choices = """
            ----- User Database Menu -----
            1. Create User
            2. Read All Users
            3. Read User by ID
            4. Update User
            5. Delete User
            0. Exit
            """;

    private static final Scanner scanner = new Scanner(System.in);

    // Scanner reads data from the specified stream
    private final Connection connection;

    public Menu(Connection con) {
        this.connection = con;
    }

    private void CreateUser()
            throws SQLException {
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

    private void ReadUsers()
            throws SQLException {
        String allUsers = UserDbWorker.Read(connection);
        StringExts.printGreen(allUsers);
    }

    private void FindUser()
            throws SQLException, InputMismatchException {
        StringExts.printPurple("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String userById = UserDbWorker.Read(connection, userId);
        StringExts.printGreen(userById);
    }

    private void UpdateUser()
            throws SQLException, InputMismatchException {
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

    private void DeleteUser()
            throws SQLException, InputMismatchException {
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

    public void run() {
        boolean exit = false;
        do {
            try {
                StringExts.printBlue(choices);

                StringExts.printPurple("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> CreateUser();
                    case 2 -> ReadUsers();
                    case 3 -> FindUser();
                    case 4 -> UpdateUser();
                    case 5 -> DeleteUser();
                    case 0 -> exit = true;
                    default -> StringExts.printYellow("Invalid choice. Please try again.");
                }

                System.out.println();
            } catch (SQLException ex) {
                // writes a line to the console and moves to the next line
                StringExts.printRed("Something went wrong: " + ex.getMessage());
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                StringExts.printRed("Enter integer next time");
            }
        } while (!exit);
    }
}
