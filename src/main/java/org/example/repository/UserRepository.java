package org.example.repository;

import org.example.domain.User;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserWithIdNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class UserRepository {
    private final HashMap <String, User> users = new HashMap<>();

    public UserRepository() {}

    public User addUser(User user) {
        boolean found = users.values().stream()
                .anyMatch(tempUser -> user.getLogin().equals(tempUser.getLogin()));
        if (users.containsKey(user.getId()) || found) {
            throw new UserAlreadyExistsException(user.getLogin());
        }

        users.put(user.getId(), user);
        return user;
    }

    public User getUser(String id) {
        if (!users.containsKey(id)) {
            throw new UserWithIdNotFoundException(id);
        }
        return users.get(id);
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> values = new ArrayList<>(users.values());
        return values;
    }

    public User getUserByLogin(String login) {
        User userFound = users.values().stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst()
                .orElse(null);
        return userFound;
    }

    public boolean containsKey(String id) {
        return users.containsKey(id);
    }

    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new UserWithIdNotFoundException(user.getId());
        }
        users.remove(user.getId());
        users.put(user.getId(), user);
        return user;
    }

}
