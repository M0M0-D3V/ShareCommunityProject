package com.sharecommunity.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sharecommunity.demo.models.User;
import com.sharecommunity.demo.repositories.UserRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepository.save(user);
    }

    // find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // find user by id
    public User findUserById(Long id) {
        Optional<User> u = userRepository.findById(id);

        if (u.isPresent()) {
            return u.get();
        } else {
            return null;
        }
    }

    // find all other users
    public List<User> findAllOtherUsers(Long id) {
        return userRepository.findByIdNot(id);
    }

    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if (user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if (BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }

    // friend request from me to user
    public void friendRequest(User me, User user) {
        me.addUsersRequestedTo(user);
        user.addRequestReceivedFrom(me);
        System.out.println("Added both me and user to requested and received");
        userRepository.save(me);
        userRepository.save(user);
        List<User> myRequestedList = me.getUsersRequestedTo();
        for (User requestee : myRequestedList) {
            System.out.println(requestee.getUsername());
        }
    }
}