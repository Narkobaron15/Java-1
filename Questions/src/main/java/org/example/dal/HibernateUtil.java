package org.example.dal;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtil implements AutoCloseable {
    private static final Logger logger;
    private static final Configuration configuration;
    private final SessionFactory sessionFactory;

    static {
        logger = java.util.logging.Logger.getLogger("org.hibernate");
        logger.setLevel(Level.SEVERE);
        // Create a Hibernate configuration object.
        configuration = new Configuration().configure();
    }

    public HibernateUtil() {
        // Create a SessionFactory object.
        sessionFactory = configuration.buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    // Create a Session object.
    public Session getSession() {
        return this.sessionFactory.openSession();
    }

    public void close() throws HibernateException {
        // Close the SessionFactory object.
        this.sessionFactory.close();
        logger.setLevel(Level.ALL);
    }
}
