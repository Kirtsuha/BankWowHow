package org.example.repository;

import org.example.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class UserRepository {
    private final HashMap <String, User> users = new HashMap<>();

    public UserRepository() {}

    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> values = new ArrayList<>(users.values());
        return values;
    }

    public User getUserByLogin(String login) {
        return users.values().stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst()
                .orElse(null);
    }

    public boolean containsKey(String id) {
        return users.containsKey(id);
    }

}
