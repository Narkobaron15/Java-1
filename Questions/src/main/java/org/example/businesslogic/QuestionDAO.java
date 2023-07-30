package org.example.businesslogic;

import org.example.models.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.*;

public class QuestionDAO implements IDAO<Question, Long> {
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
    public Question readById(Long id) {
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

    // Get random questions
    public List<Question> getRandomQuestions(int count) {
        try (Session session = sessionFactory.openSession()) {
            // Get the total count of questions in the database
            Criteria criteria = session.createCriteria(Question.class);
            Long totalQuestions = (Long) criteria.setProjection(org.hibernate.criterion.Projections.rowCount()).uniqueResult();

            // If the requested count is greater than the total count, return all questions
            if (count >= totalQuestions) {
                return this.readAll();
            }

            // Generate a random set of unique question IDs
            Random random = new Random();
            List<Long> randomIds = getRandomUniqueIds(totalQuestions.intValue(), count, random);

            // Retrieve the random questions using the IDs
            criteria = session.createCriteria(Question.class);
            criteria.add(Restrictions.in("id", randomIds));
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Helper method to generate a list of random unique IDs
    private List<Long> getRandomUniqueIds(int maxId, int count, Random random) {
        List<Long> randomIds = new ArrayList<>();

        // Populate the list with unique random IDs
        while (randomIds.size() < count) {
            Long randomId = random.nextLong(maxId) + 1; // IDs start from 1

            if (!randomIds.contains(randomId))
                randomIds.add(randomId);
        }

        return randomIds;
    }
}
