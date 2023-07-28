package org.example.seeders;

import org.example.dataaccess.*;
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

        Question question1 = new Question();
        question1.setQuestionType("Multiple Choice");
        question1.setText("What is the capital of France?");
        questions.add(question1);

        Question question2 = new Question();
        question2.setQuestionType("True/False");
        question2.setText("Is the Earth flat?");
        questions.add(question2);

        // Add more questions as needed

        return questions;
    }

    // Helper method to create a list of sample question responses
    private List<QuestionResponse> createQuestionResponses(List<Question> questions) {
        List<QuestionResponse> questionResponses = new ArrayList<>();

        // Question 1 responses
        QuestionResponse response1 = new QuestionResponse();
        response1.setTrue(false);
        response1.setText("Berlin");
        response1.setQuestion(questions.get(0));
        questionResponses.add(response1);

        QuestionResponse response2 = new QuestionResponse();
        response2.setTrue(true);
        response2.setText("Paris");
        response2.setQuestion(questions.get(0));
        questionResponses.add(response2);

        // Question 2 responses
        QuestionResponse response3 = new QuestionResponse();
        response3.setTrue(false);
        response3.setText("True");
        response3.setQuestion(questions.get(1));
        questionResponses.add(response3);

        QuestionResponse response4 = new QuestionResponse();
        response4.setTrue(true);
        response4.setText("False");
        response4.setQuestion(questions.get(1));
        questionResponses.add(response4);

        // Add more question responses as needed

        return questionResponses;
    }
}
