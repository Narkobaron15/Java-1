package org.example;
import org.example.businesslogic.*;
import org.example.models.*;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MillionaireGameMenu {
    private Scanner scanner;
    private QuestionDAO questionDAO;
    private QuestionResponseDAO responseDAO;
    private int rightAnswers;

    public MillionaireGameMenu(QuestionDAO questionDAO, QuestionResponseDAO responseDAO) {
        scanner = new Scanner(System.in);
        this.questionDAO = questionDAO;
        this.responseDAO = responseDAO;
        rightAnswers = 0;
    }
    public MillionaireGameMenu(SessionFactory sessionFactory) {
        this(
                new QuestionDAO(sessionFactory),
                new QuestionResponseDAO(sessionFactory)
        );
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("----- Who Wants to Be a Millionaire -----");
            System.out.println("1. Play");
            System.out.println("2. Add a question");
            System.out.println("3. Remove a question");
            System.out.println("4. Edit a question");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        playGame();
                        break;
                    case 2:
                        addQuestion();
                        break;
                    case 3:
                        removeQuestion();
                        break;
                    case 4:
                        editQuestion();
                        break;
                    case 0:
                        System.out.println("Thank you for playing!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
                choice = -1; // Set to an invalid choice to trigger the loop again
            }
        } while (choice != 0);
    }

    private void playGame() {
        System.out.print("Enter the count of questions you want to answer: ");
        int count = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<Question> questions = questionDAO.getRandomQuestions(count);
        System.out.println("Let's start the game!");

        for (Question question : questions) {
            System.out.println(question);
            System.out.print("Enter your answer (1-" + question.getQuestionResponses().size() + "): ");
            int userAnswer = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (userAnswer >= 1 && userAnswer <= question.getQuestionResponses().size()) {
                QuestionResponse response = question.getQuestionResponses().get(userAnswer - 1);
                if (response.isTrue()) {
                    System.out.println("Correct answer!");
                    rightAnswers++;
                } else {
                    System.out.println("Wrong answer! The correct answer was: " +
                            question.getCorrectAnswer().toString());
                }
            } else {
                System.out.println("Invalid answer. Skipping to the next question.");
            }
        }

        System.out.println("Game Over!\nScore: " + getScore(rightAnswers, questions.size()) + "/12");
        rightAnswers = 0;
    }

    // get 12-point based score
    private double getScore(int rightAnswers, int allAnswers) {
        return (double) Math.round(((double) (12 * rightAnswers) / allAnswers) * 100) / 100;
    }

    private void addQuestion() {
        System.out.print("Enter the question type: ");
        String questionType = scanner.nextLine();

        System.out.print("Enter the question text: ");
        String text = scanner.nextLine();

        Question question = new Question(questionType, text);

        System.out.print("Enter the number of responses: ");
        int numResponses = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        question.setQuestionResponses(new ArrayList<>());
        for (int i = 1; i <= numResponses; i++) {
            System.out.print("Enter response " + i + " text: ");
            String responseText = scanner.nextLine();

            System.out.print("Is it the correct answer? (true/false): ");
            boolean isTrue = scanner.nextBoolean();
            scanner.nextLine(); // Consume the newline character

            QuestionResponse response = new QuestionResponse(isTrue, responseText, question);
            question.getQuestionResponses().add(response);
        }

        questionDAO.create(question);
        question.getQuestionResponses().forEach(responseDAO::create);
    }

    private void removeQuestion() {
        List<Question> questions = questionDAO.readAll();
        if (questions.isEmpty()) {
            System.out.println("No questions available to remove.");
            return;
        }

        System.out.println("Select the question ID to remove:");
        for (Question question : questions) {
            System.out.println(question.getId() + ". " + question.getText());
        }
        System.out.println("0. Exit");

        long selectedId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        if (selectedId == 0)
            return;

        Question question = questionDAO.readById(selectedId);
        if (question != null) {
            question.getQuestionResponses().forEach(responseDAO::delete);
            questionDAO.delete(question);
            System.out.println("Question removed successfully.");
        } else {
            System.out.println("Question with ID " + selectedId + " not found.");
        }
    }

    private void editQuestion() {
        List<Question> questions = questionDAO.readAll();
        if (questions.isEmpty()) {
            System.out.println("No questions available to edit.");
            return;
        }

        System.out.println("Select the question ID to edit:");
        for (Question question : questions) {
            System.out.println(question.getId() + ". " + question.getText());
        }
        System.out.println("0. Exit");

        long selectedId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline character

        if (selectedId == 0)
            return;

        Question question = questionDAO.readById(selectedId);
        if (question != null) {
            System.out.print("Enter the new question text: ");
            String newText = scanner.nextLine();
            question.setText(newText);

            // Update responses (assuming the number of responses remains the same)
            List<QuestionResponse> responses = question.getQuestionResponses();
            for (int i = 0; i < responses.size(); i++) {
                System.out.print("Enter new response " + (i + 1) + " text: ");
                String newResponseText = scanner.nextLine();
                responses.get(i).setText(newResponseText);
            }

            questionDAO.update(question);
            responses.forEach(responseDAO::update);
            System.out.println("Question updated successfully.");
        } else {
            System.out.println("Question with ID " + selectedId + " not found.");
        }
    }
}
