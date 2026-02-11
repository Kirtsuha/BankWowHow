package org.example.repository;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserWithIdNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(User user) {
        var userExists = sessionFactory.getCurrentSession().find(User.class, user.getId()) != null;
        if (userExists) {
            throw new UserAlreadyExistsException(user.getLogin());
        }
        sessionFactory.getCurrentSession().persist(user);
    }

    public User read(Long id) {
        var user = sessionFactory.getCurrentSession().find(User.class, id);
        if (user == null) {
            throw new UserWithIdNotFoundException(id.toString());
        }
        return user;
    }

    public List<User> getAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("FROM User", User.class).stream().toList();
    }

//    public User getUserByLogin(String login) {
//        return sessionFactory.getCurrentSession()
//                .createQuery("FROM User U WHERE U.login = :user_login", User.class)
//                .setParameter("user_login", login).stream().findFirst().orElse(null);
//    }

    public void update(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

}
