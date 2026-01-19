package org.example.service;

import org.example.domain.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final ObjectProvider<User> objectProvider;

    @Autowired
    public UserService(UserRepository userRepository, ObjectProvider<User> objectProvider) {
        this.userRepository = userRepository;
        this.objectProvider = objectProvider;
    }

    public User createUser(String login) {
        String id = UUID.randomUUID().toString();
        ArrayList<String> accountIdList = new ArrayList<>();
        User user = objectProvider.getObject(
                id,
                login,
                accountIdList);
        userRepository.addUser(user);
        return user;
    }

    public void addAccountToUser(String userId, String accountId) {
        User tempUser = userRepository.getUser(userId);
        tempUser.getAccountIdList().add(accountId);
    }

    public void removeAccountFromUser(String userId, String accountId) {
        User tempUser = userRepository.getUser(userId);
        tempUser.getAccountIdList().remove(accountId);
    }

    public User getUser(String id) {
        return userRepository.getUser(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
