package org.example.repository;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component

public class TransactionHelper<T> {
    private SessionFactory sessionFactory;
    public TransactionHelper(@Autowired SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void makeTransactional(Session session, Consumer<T> action) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            //some work
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
