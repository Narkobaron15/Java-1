package org.example.orm;

import org.example.models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUtil implements AutoCloseable {
    private static final Logger logger = java.util.logging.Logger.getLogger("org.hibernate");

    private final SessionFactory sessionFactory;

    public DbUtil() {
        logger.setLevel(Level.SEVERE);

        Configuration configuration = new Configuration().configure(new File("hibernate.cfg.xml"));
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        Metadata metadata = new MetadataSources(registryBuilder.build()).addAnnotatedClass(User.class).buildMetadata();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

        logger.setLevel(Level.ALL);
    }

    public SessionFactory getSessionFactory() throws HibernateException {
        return sessionFactory;
    }

    public Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    @Override
    public void close() {
        logger.setLevel(Level.SEVERE);
        sessionFactory.close();
        logger.setLevel(Level.ALL);
    }
}
