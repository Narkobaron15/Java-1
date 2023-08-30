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
        try (DbUtil util = new DbUtil(); UserDAO dao = new UserDAO(util.getSessionFactory())) {
            Menu menu = new Menu(dao);
            menu.run();
        } catch (Exception ex) {
            StringExts.printRed("Something went wrong: " + ex.getMessage());
        }
    }
}