package org.example.dataaccess;

import org.example.models.QuestionResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class QuestionResponseDAO {
    private final SessionFactory sessionFactory;

    public QuestionResponseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Create
    public void create(QuestionResponse response) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Read all
    public List<QuestionResponse> readAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM QuestionResponse", QuestionResponse.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Read by ID
    public QuestionResponse readById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(QuestionResponse.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update
    public void update(QuestionResponse response) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Remove (Delete)
    public void delete(QuestionResponse response) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(response);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
