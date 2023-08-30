package org.example;

import org.example.exts.StringExts;
import org.example.models.User;
import org.example.orm.UserDAO;

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
    // Scanner reads data from the specified stream
    private static final Scanner scanner = new Scanner(System.in);

    private final UserDAO DAO;

    public Menu(UserDAO userDAO) {
        this.DAO = userDAO;
    }

    private void createUser() {
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

    private void readUsers() {
        for (User u: DAO.readAll()) {
            StringExts.printGreen(u.toString());
        }
    }

    private void findUser() throws InputMismatchException {
        StringExts.printPurple("Enter user ID: ");
        Long userId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        User userById = DAO.readById(userId);

        if (userById == null) StringExts.printRed("User is not found");
        else StringExts.printGreen(userById.toString());
    }

    private void updateUser() throws InputMismatchException {
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

    private void deleteUser() throws InputMismatchException {
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

    public void run() {
        boolean exit = false;
        do {
            try {
                StringExts.printBlue(choices);

                StringExts.printPurple("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1 -> createUser();
                    case 2 -> readUsers();
                    case 3 -> findUser();
                    case 4 -> updateUser();
                    case 5 -> deleteUser();
                    case 0 -> exit = true;
                    default -> StringExts.printYellow("Invalid choice. Please try again.");
                }

                System.out.println();
            } catch (InputMismatchException ex) {
                scanner.nextLine(); // Consume bad characters
                StringExts.printRed("Enter integer next time");
            }
        } while (!exit);
    }
}
