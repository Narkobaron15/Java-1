package org.example.orm;

import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;

/**
 * Data Access Object (DAO) for performing CRUD operations on User entities.
 */
public class UserDAO implements AutoCloseable {
    private final Session session;

    /**
     * Constructs a new UserDAO instance with the given SessionFactory.
     *
     * @param sessionFactory the Hibernate SessionFactory
     */
    public UserDAO(SessionFactory sessionFactory) {
        session = sessionFactory.openSession();
    }

    /**
     * Closes the DAO and releases any resources associated with the Session.
     */
    public void close() {
        if (session != null) {
            session.close();
        }
    }

    /**
     * Creates a new user with the provided username and email.
     *
     * @param username the username of the user
     * @param email    the email of the user
     * @return true if the user creation is successful, false otherwise
     */
    public boolean create(String username, String email) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of all users in the database, or null if an error occurs
     */
    public List<User> readAll() {
        try {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            JpaCriteriaQuery<User> query = builder.createQuery(User.class);
            JpaRoot<User> root = query.from(User.class);
            query.select(root);
            Query<User> q = session.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a user from the database by the given user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User object corresponding to the user ID, or null if not found or an error occurs
     */
    public User readById(Long userId) {
        try {
            return session.get(User.class, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the user with the given user ID, setting the new username and email values.
     *
     * @param userId   the ID of the user to update
     * @param username the new username value
     * @param email    the new email value
     * @return true if the user update is successful, false otherwise
     */
    public boolean update(Long userId, String username, String email) {
        try {
            User user = session.get(User.class, userId);
            if (user != null) {
                user.setUsername(username);
                user.setEmail(email);
                session.beginTransaction();
                session.merge(user);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes the user with the given user ID from the database.
     *
     * @param userId the ID of the user to delete
     * @return true if the user deletion is successful, false otherwise
     */
    public boolean delete(Long userId) {
        try {
            User user = session.get(User.class, userId);
            if (user != null) {
                session.beginTransaction();
                session.remove(user);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
