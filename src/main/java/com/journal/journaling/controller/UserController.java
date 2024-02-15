package com.journal.journaling.controller;

import com.journal.journaling.entity.JournalEntry;
import com.journal.journaling.entity.User;
import com.journal.journaling.service.JournalEntryService;
import com.journal.journaling.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/user")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable(name = "username") ObjectId userId) {

        try {
            userService.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{username}")
    public ResponseEntity<User> getUser(@PathVariable(name = "username") String username) {
        try {
            User user = userService.findByUsername(username);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{username}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "username") String username, @RequestBody User user) {
        User userFound = userService.findByUsername(username);
        try {

            if (userFound == null) {
                return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
            }

            userFound.setUsername(user.getUsername());
            userFound.setPassword(user.getPassword());
            userService.saveUser(userFound);

            return new ResponseEntity<>(userFound, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
