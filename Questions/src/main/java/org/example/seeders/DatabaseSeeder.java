package org.example.seeders;

import org.example.businesslogic.*;
import org.example.models.*;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {
    private final QuestionDAO questionDAO;
    private final QuestionResponseDAO questionResponseDAO;

    public DatabaseSeeder(SessionFactory sessionFactory) {
        this.questionDAO = new QuestionDAO(sessionFactory);
        this.questionResponseDAO = new QuestionResponseDAO(sessionFactory);
    }

    // Method to seed the database with questions and question responses
    public void seedDatabase() {
        // Check if any questions exist in the database
        List<Question> questions = questionDAO.readAll();
        if (questions.size() > 0) {
            System.out.println("Database already seeded with questions.");
            return;
        }

        // Create and save questions
        List<Question> questionsToCreate = createQuestions();
        for (Question question : questionsToCreate) {
            questionDAO.create(question);
        }

        // Check if any question responses exist in the database
        List<QuestionResponse> questionResponses = questionResponseDAO.readAll();
        if (questionResponses.size() > 0) {
            System.out.println("Database already seeded with question responses.");
            return;
        }

        // Create and save question responses
        List<QuestionResponse> questionResponsesToCreate = createQuestionResponses(questionsToCreate);
        for (QuestionResponse response : questionResponsesToCreate) {
            questionResponseDAO.create(response);
        }
    }

    // Helper method to create a list of sample questions
    private List<Question> createQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("Multiple Choice", "What is the capital of France?"));                                               // 1
        questions.add(new Question("True/False", "Is the Earth flat?"));                                                                // 2
        questions.add(new Question("Multiple Choice", "What is the largest planet in our solar system?"));                              // 3
        questions.add(new Question("True/False", "The Great Wall of China is visible from space."));                                    // 4
        questions.add(new Question("Fill in the Blank", "The four primary states of matter are solid, liquid, gas, and ___________.")); // 5
        questions.add(new Question("Multiple Choice", "Which famous scientist developed the theory of general relativity?"));           // 6
        questions.add(new Question("True/False", "Dolphins are mammals."));                                                             // 7
        questions.add(new Question("Multiple Choice", "What is the chemical symbol for water?"));                                       // 8
        questions.add(new Question("True/False", "The Eiffel Tower is located in London."));                                            // 9
        questions.add(new Question("Multiple Choice", "Who wrote the famous play \"Romeo and Juliet\"?"));                              // 10
        questions.add(new Question("True/False", "The human body has 206 bones."));                                                     // 11
        questions.add(new Question("Multiple Choice", "Which planet is known as the \"Red Planet\"?"));                                 // 12

        return questions;
    }

    // Helper method to create a list of sample question responses
    private List<QuestionResponse> createQuestionResponses(List<Question> questions) {
        List<QuestionResponse> questionResponses = new ArrayList<>();

        // Question 1 responses
        questionResponses.add(new QuestionResponse(false, "Berlin", questions.get(0)));
        questionResponses.add(new QuestionResponse(true, "Paris", questions.get(0)));

        // Question 2 responses
        questionResponses.add(new QuestionResponse(false, "True", questions.get(1)));
        questionResponses.add(new QuestionResponse(true, "False", questions.get(1)));

        // Question 3 responses
        questionResponses.add(new QuestionResponse(true, "Jupiter", questions.get(2)));
        questionResponses.add(new QuestionResponse(false, "Earth", questions.get(2)));
        questionResponses.add(new QuestionResponse(false, "Mars", questions.get(2)));
        questionResponses.add(new QuestionResponse(false, "Saturn", questions.get(2)));

        // Question 4 responses
        questionResponses.add(new QuestionResponse(false, "True", questions.get(3)));
        questionResponses.add(new QuestionResponse(true, "False", questions.get(3)));

        // Question 5 responses
        questionResponses.add(new QuestionResponse(true, "plasma", questions.get(4)));
        questionResponses.add(new QuestionResponse(false, "rectangle", questions.get(4)));
        questionResponses.add(new QuestionResponse(false, "hypersolid", questions.get(4)));
        questionResponses.add(new QuestionResponse(false, "hyperliquid", questions.get(4)));

        // Question 6 responses
        questionResponses.add(new QuestionResponse(false, "Isaac Newton", questions.get(5)));
        questionResponses.add(new QuestionResponse(true, "Albert Einstein", questions.get(5)));

        // Question 7 responses
        questionResponses.add(new QuestionResponse(true, "True", questions.get(6)));
        questionResponses.add(new QuestionResponse(false, "False", questions.get(6)));

        // Question 8 responses
        questionResponses.add(new QuestionResponse(true, "H2O", questions.get(7)));
        questionResponses.add(new QuestionResponse(false, "CO2", questions.get(7)));
        questionResponses.add(new QuestionResponse(false, "O2", questions.get(7)));
        questionResponses.add(new QuestionResponse(false, "N2", questions.get(7)));

        // Question 9 responses
        questionResponses.add(new QuestionResponse(false, "True", questions.get(8)));
        questionResponses.add(new QuestionResponse(true, "False", questions.get(8)));

        // Question 10 responses
        questionResponses.add(new QuestionResponse(true, "William Shakespeare", questions.get(9)));
        questionResponses.add(new QuestionResponse(false, "Jane Austen", questions.get(9)));

        // Question 11 responses
        questionResponses.add(new QuestionResponse(true, "True", questions.get(10)));
        questionResponses.add(new QuestionResponse(false, "False", questions.get(10)));

        // Question 12 responses (debatable though)
        questionResponses.add(new QuestionResponse(false, "Venus", questions.get(11)));
        questionResponses.add(new QuestionResponse(true, "Mars", questions.get(11)));
        questionResponses.add(new QuestionResponse(false, "Mercury", questions.get(11)));
        questionResponses.add(new QuestionResponse(false, "Neptune", questions.get(11)));

        return questionResponses;
    }
}
