package org.example.repository;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserWithIdNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepository {

    public void create(Session session, User user) {
        var userExists = getUserByLogin(session, user.getLogin()) != null;
        if (userExists) {
            throw new UserAlreadyExistsException(user.getLogin());
        }
        session.persist(user);
    }

    public User read(Session session, Long id) {
        var user = session.find(User.class, id);
        if (user == null) {
            throw new UserWithIdNotFoundException(id.toString());
        }
        return user;
    }

    public List<User> getAllUsers(Session session) {
        return session.createQuery("FROM User", User.class).stream().toList();
    }

    private User getUserByLogin(Session session, String login) {
        return session
                .createQuery("FROM User U WHERE U.login = :user_login", User.class)
                .setParameter("user_login", login).stream().findFirst().orElse(null);
    }

    public void update(Session session, User user) {
        session.persist(user);
    }

}
