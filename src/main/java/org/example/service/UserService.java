package org.example.service;

import org.example.domain.User;
import org.example.repository.TransactionHelper;
import org.example.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final TransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    @Autowired
    public UserService(UserRepository userRepository, TransactionHelper transactionHelper, SessionFactory sessionFactory) {
        this.userRepository = userRepository;
        this.transactionHelper = transactionHelper;
        this.sessionFactory = sessionFactory;
    }

    public User createUser(String login) {
        User user = new User();
        user.setLogin(login);
        transactionHelper.makeTransactional(sessionFactory.openSession(), session ->  {
            userRepository.create(session, user);
            return null;
        });
        return user;
    }

//    public void addAccountToUser(Long userId, Long accountId) {
//        User tempUser = userRepository.read(userId);
//        tempUser.getAccountIdList().add(accountId);
//    }
//
//    public void removeAccountFromUser(String userId, String accountId) {
//        User tempUser = userRepository.read(userId);
//        tempUser.getAccountIdList().remove(accountId);
//    }

    public User getUser(Long id) {
        return transactionHelper.makeTransactional(sessionFactory.openSession(), session ->
            userRepository.read(session, id)
        );
    }

    public List<User> getAllUsers() {
        return transactionHelper.makeTransactional(sessionFactory.openSession(), session -> userRepository.getAllUsers(session));
    }

}
