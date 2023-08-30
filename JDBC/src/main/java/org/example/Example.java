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
                Menu menu = new Menu(dbConn);
                menu.run();
            }
        }
        catch (Exception ex) {
            // writes a line to the console and moves to the next line
            StringExts.printRed("Couldn't connect to the database.\nDetails: " + ex.getMessage());
        }
    }
}