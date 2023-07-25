package org.example.databaseseeders;

import org.example.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class DatabaseSeeder {
    private final SessionFactory sessionFactory;

    public DatabaseSeeder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void seedDatabase() {
        seedCategories();
        seedProducts();
        seedProductImages();
        seedQuestions();
        seedQuestionResponses();
        seedRoles();
        seedUsers();
    }

    public void seedCategories() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Category> existingCategories = session.createQuery("FROM Category", Category.class).list();
            if (existingCategories.isEmpty()) {
                Category category1 = new Category();
                category1.setName("Category 1");
                category1.setCreated_at(new Date());
                session.save(category1);

                Category category2 = new Category();
                category2.setName("Category 2");
                category2.setCreated_at(new Date());
                session.save(category2);
            }
            transaction.commit();
        }
    }

    public void seedProducts() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Product> existingProducts = session.createQuery("FROM Product", Product.class).list();
            if (existingProducts.isEmpty()) {
                Category category1 = session.createQuery("FROM Category WHERE name = 'Category 1'", Category.class)
                        .getSingleResult();

                Category category2 = session.createQuery("FROM Category WHERE name = 'Category 2'", Category.class)
                        .getSingleResult();

                Product product1 = new Product();
                product1.setName("Product 1");
                product1.setCreatedAt(new Date());
                product1.setCategory(category1);
                session.save(product1);

                Product product2 = new Product();
                product2.setName("Product 2");
                product2.setCreatedAt(new Date());
                product2.setCategory(category2);
                session.save(product2);
            }
            transaction.commit();
        }
    }

    public void seedProductImages() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<ProductImage> existingImages = session.createQuery("FROM ProductImage", ProductImage.class).list();
            if (existingImages.isEmpty()) {
                List<Product> products = session.createQuery("FROM Product", Product.class).list();
                for (Product product : products) {
                    ProductImage image1 = new ProductImage();
                    image1.setName("Image 1 for " + product.getName());
                    image1.setCreatedAt(new Date());
                    image1.setProduct(product);
                    session.save(image1);

                    ProductImage image2 = new ProductImage();
                    image2.setName("Image 2 for " + product.getName());
                    image2.setCreatedAt(new Date());
                    image2.setProduct(product);
                    session.save(image2);
                }
            }
            transaction.commit();
        }
    }

    public void seedQuestions() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Question> existingQuestions = session.createQuery("FROM Question", Question.class).list();
            if (existingQuestions.isEmpty()) {
                Question question1 = new Question();
                question1.setQuestionType("Type 1");
                question1.setText("Question 1");
                session.save(question1);

                Question question2 = new Question();
                question2.setQuestionType("Type 2");
                question2.setText("Question 2");
                session.save(question2);
            }
            transaction.commit();
        }
    }

    public void seedQuestionResponses() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<QuestionResponse> existingResponses = session
                    .createQuery("FROM QuestionResponse", QuestionResponse.class).list();
            if (existingResponses.isEmpty()) {
                List<Question> questions = session.createQuery("FROM Question", Question.class).list();
                for (Question question : questions) {
                    QuestionResponse response1 = new QuestionResponse();
                    response1.setTrue(true);
                    response1.setText("Response 1 for " + question.getText());
                    response1.setQuestion(question);
                    session.save(response1);

                    QuestionResponse response2 = new QuestionResponse();
                    response2.setTrue(false);
                    response2.setText("Response 2 for " + question.getText());
                    response2.setQuestion(question);
                    session.save(response2);
                }
            }
            transaction.commit();
        }
    }

    public void seedRoles() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Role> existingRoles = session.createQuery("FROM Role", Role.class).list();
            if (existingRoles.isEmpty()) {
                Role role1 = new Role();
                role1.setName("Role 1");
                session.save(role1);

                Role role2 = new Role();
                role2.setName("Role 2");
                session.save(role2);
            }
            transaction.commit();
        }
    }

    public void seedUsers() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<User> existingUsers = session.createQuery("FROM User", User.class).list();
            if (existingUsers.isEmpty()) {
                Role role1 = session.createQuery("FROM Role WHERE name = 'Role 1'", Role.class).getSingleResult();
                Role role2 = session.createQuery("FROM Role WHERE name = 'Role 2'", Role.class).getSingleResult();

                User user1 = new User("user1", "John", "Doe", "user1@example.com", "1234567890", "password1");
                user1.setRoles(Set.of(role1));
                session.save(user1);

                User user2 = new User("user2", "Jane", "Smith", "user2@example.com", "9876543210", "password2");
                user2.setRoles(Set.of(role2));
                session.save(user2);
            }
            transaction.commit();
        }
    }
}
