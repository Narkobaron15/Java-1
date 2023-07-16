package org.example.exts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Common utilities for database usage
 */
public class DbManipulations {
    /**
     * Ensure that database exists
     * If not, a new database is created
     * @param connection Connection to the database server
     * @param dbName Name of database
     */
    public static void ensureDbExists(Connection connection, String dbName) {
        try (Statement stmt = connection.createStatement()) {
            if(dbExists(connection, dbName)){
//                System.out.println("Database with the name " + dbName + " already exists");
                return;
            }

            String sql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if database exists
     * @param connection Connection to the database server
     * @param dbName Name of database
     * @return Database existence status
     * @throws SQLException Exception thrown on closed connection
     */
    public static boolean dbExists(Connection connection, String dbName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Check if the database exists
            String checkDbQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + dbName + "'";
            ResultSet resultSet = stmt.executeQuery(checkDbQuery);

            return resultSet.next();
        }
    }

    /**
     * Check if table exists
     * @param connection Connection to the database
     * @param tableName Name of table
     * @return Table existence status
     */
    public static boolean tableExists(Connection connection, String tableName) {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(tableName)) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }
}
