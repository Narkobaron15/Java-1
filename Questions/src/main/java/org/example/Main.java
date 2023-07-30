package org.example;

import org.example.dal.HibernateUtil;
import org.example.businesslogic.*;
import org.example.seeders.DatabaseSeeder;

import java.util.Random;

public class Main {
    private final static Random random = new Random();

    public static void main(String[] args) {
        // TODO:
        // Add menu and play mode

        try (HibernateUtil util = new HibernateUtil()){
            new DatabaseSeeder(util.getSessionFactory()).seedDatabase();

            MillionaireGameMenu menu = new MillionaireGameMenu(util.getSessionFactory());
            menu.showMenu();
        }
    }

    // https://stackoverflow.com/questions/997482/does-java-support-default-parameter-values
}