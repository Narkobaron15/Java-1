package org.example;

import org.example.dataaccess.HibernateUtil;
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
//            CreateUser(s);
            PrintAllUsers(s);
        } catch (Exception e) {
            System.err.println("Something wrong happened: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void CreateUser(Session session) {
        User u = new User("user123", "John", "Smith", "lol2@gmail.com", "+2234567", "none");
        Transaction t = session.beginTransaction();
        session.merge(u);
        t.commit();
    }

    public static void PrintAllUsers(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        Query<User> q = session.createQuery(query);
        q.getResultList().forEach(System.out::println);
    }
}