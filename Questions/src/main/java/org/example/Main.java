package org.example;

import org.example.dal.HibernateUtil;
import org.example.dataaccess.QuestionDAO;
import org.example.dataaccess.QuestionResponseDAO;
import org.example.models.Question;
import org.example.seeders.DatabaseSeeder;

public class Main {
    public static void main(String[] args) {
        // TODO:
        // Add menu and play mode

        try (HibernateUtil util = new HibernateUtil()){
            new DatabaseSeeder(util.getSessionFactory()).seedDatabase();

            QuestionDAO questionDAO = new QuestionDAO(util.getSessionFactory());
            QuestionResponseDAO responseDAO = new QuestionResponseDAO(util.getSessionFactory());

            System.out.println();

            for (Question q:
                 questionDAO.readAll()) {
                System.out.println(q);
            }
        }
    }
}