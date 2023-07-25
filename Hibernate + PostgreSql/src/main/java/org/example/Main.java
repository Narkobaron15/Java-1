package org.example;

import com.sun.istack.NotNull;
import org.example.dataaccess.HibernateUtil;
import org.example.databaseseeders.DatabaseSeeder;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

// https://www.baeldung.com/jpa-joincolumn-vs-mappedby

public class Main {
    public static void main(String[] args) {
        try (HibernateUtil util = new HibernateUtil(); Session s = util.getSession()) {
            // if nothing happens, change hbm2ddl.auto to create
            DatabaseSeeder seeder = new DatabaseSeeder(util.getSessionFactory());
            seeder.seedDatabase();

            PrintAllUsers(s);
        } catch (Exception e) {
            System.err.println("Something wrong happened: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void CreateUser(@NotNull Session session) {
        User u = new User("user123", "John", "Smith", "lol2@gmail.com", "+2234567", "none");
        Transaction t = session.beginTransaction();
        session.merge(u);
        t.commit();
    }

    public static void PrintAllUsers(@NotNull Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Query<User> q = session.createQuery(query);
        q.getResultList().forEach(System.out::println);
    }
}