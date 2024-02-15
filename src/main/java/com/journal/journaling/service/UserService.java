package com.journal.journaling.service;

import com.journal.journaling.entity.JournalEntry;
import com.journal.journaling.entity.User;
import com.journal.journaling.repository.JournalEntryRepository;
import com.journal.journaling.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public Optional<User> findById(ObjectId userId) {
        return userRepository.findById(userId);
    }

    public boolean deleteById(ObjectId userId) {

        userRepository.deleteById(userId);
        return true;
    }

    public User updateUser(ObjectId userId, User user) {
        User userFound = this.findById(userId).orElse(null);

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            userFound.setUsername(user.getUsername());
        }

        this.saveUser(userFound);
        return userFound;

    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
