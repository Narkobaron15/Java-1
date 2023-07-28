package org.example.dataaccess;

import org.example.models.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class QuestionDAO {
    private final SessionFactory sessionFactory;

    public QuestionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Create
    public void create(Question question) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(question);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read all
    public List<Question> readAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Question", Question.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Read by ID
    public Question readById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Question.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update
    public void update(Question question) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(question);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete
    public void delete(Question question) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(question);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
