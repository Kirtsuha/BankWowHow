package org.example.service;

import org.example.domain.Account;
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
    private ObjectProvider<User> objectProvider;

    @Autowired
    public UserService(UserRepository userRepository, ObjectProvider<User> objectProvider) {
        this.userRepository = userRepository;
        this.objectProvider = objectProvider;
    }

    public User createUser(String login) {
        String id = UUID.randomUUID().toString();
        ArrayList<Account> accountList = new ArrayList<Account>();
        User user = objectProvider.getObject(
                id,
                login,
                accountList);
        userRepository.addUser(user);
        return user;
    }

    public User addAccountToUser(String userId, Account account) {
        User tempUser = userRepository.getUser(userId);
        tempUser.getAccountList().add(account);
        return tempUser;
    }

    public void removeAccountFromUser(String userId, Account account) {
        User tempUser = userRepository.getUser(userId);
        tempUser.getAccountList().remove(account);
    }

    public User getUser(String id) {
        return userRepository.getUser(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
