package org.example;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.example.exts.DbManipulations;

import java.sql.*;

/**
 * User's CRUD
 */
public class UserDbWorker {
    // final is the keyword for constants.
    private static final String tableName = "users";

    // Documentation in Java (same as in PHP)
    /** Creating statement and executing sql passed to it to create 'users' table
     * <p> Decorator {@code '@NonNull'} is used to prevent passing null value
     *
     * @param connection Connection to the database server
     * @throws SQLException Exception thrown on closed connection
     */
    public static void ensureUsersTable(@NonNull Connection connection) throws SQLException {
        // checking if table 'users' exists
        if (DbManipulations.tableExists(connection, tableName)) {
            return;
        }

        String sql = "CREATE TABLE " + tableName + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        // creating statement
        try (Statement statement = connection.createStatement()) {
            // executing statement
            statement.executeUpdate(sql);
        }
    }

    /**
     * Create a user in database.
     *
     * @param connection Connection to the <b>database</b>
     * @param username User's pseudo
     * @param email User's email
     * @throws SQLException Exception thrown on closed connection
     */
    public static boolean Create(Connection connection, String username, String email) throws SQLException {
        if (!DbManipulations.tableExists(connection, tableName)) {
            System.out.println("Table doesn't exist.");
            return false;
        }

        // prepared statement fixes SQL Injection
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the parameters
            statement.setString(1, username);
            statement.setString(2, email);

            // Execute the statement
            statement.executeUpdate();
            return true;
        }
    }

    /**
     * Bulk read from the database
     * @param connection Connection to the <b>database</b>
     * @return String with requested data
     * @throws SQLException Exception thrown on closed connection
     */
    public static String Read(Connection connection) throws SQLException {
        if (!DbManipulations.tableExists(connection, tableName)) {
            return "Table doesn't exist.";
        }

        // prepare statement and dispose it
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM users";
            // result set holds the table of results and allows reading row-by-row
            ResultSet set = statement.executeQuery(sql);

            // array initialization
            // String[] fields = {"id", "username", "email"};

            StringBuilder builder = new StringBuilder();

            // next is moving to the next position of the set. initial position is 0
            while (set.next()) {
                    int id = set.getInt("id");
                    String username = set.getString("username");
                    String email = set.getString("email");

                    builder.append("User ID: ").append(id)
                            .append(", username: ").append(username)
                            .append(", email: ").append(email);

                if (!set.isLast()) builder.append(";\n");
            }

            return builder.toString();
        }
    }

    /**
     * Reads one user from the database
     * @param connection Connection to the <b>database</b>
     * @param userId User id to find
     * @return String with requested data
     * @throws SQLException Exception thrown on closed connection
     */
    public static String Read(Connection connection, int userId) throws SQLException {
        if (!DbManipulations.tableExists(connection, tableName)) {
            return "Table doesn't exist.";
        }

        String selectQuery = "SELECT * FROM users WHERE id = ?";
        StringBuilder builder = new StringBuilder();

        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, userId); // Replace userId with the actual user ID

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                builder.append("User with the specified ID not found.");
            else {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                builder.append("User ID: ").append(id)
                        .append("\nUsername: ").append(username)
                        .append("\nEmail: ").append(email);
            }
        }

        return builder.toString();
    }

    /**
     * Updates the user.
     * @param connection Connection to the <b>database</b>
     * @param id User's id
     * @param username New username
     * @param email New email
     * @return Boolean indicating operation status
     * @throws SQLException Exception thrown on closed connection
     */
    public static boolean Update(Connection connection, int id, String username, String email) throws SQLException {
        if (!DbManipulations.tableExists(connection, tableName))
            return false;

        String updateQuery = "UPDATE users SET username = ?, email = ? WHERE id = ?";

        // prepare statement and dispose it
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // if not found, 0 is returned
        }
    }

    /**
     * Deletes a user from the database
     * @param connection Connection to the <b>database</b>
     * @param userId ID of a user to delete
     * @return Boolean indicating operation status
     * @throws SQLException Exception thrown on closed connection
     */
    public static boolean Delete(Connection connection, int userId) throws SQLException {
        if (!DbManipulations.tableExists(connection, tableName))
            return false;

        String deleteQuery = "DELETE FROM users WHERE id = ?";

        // prepare statement and dispose it
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, userId); // Replace userId with the actual user ID to delete

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // if not found, 0 is returned
        }
    }
}
