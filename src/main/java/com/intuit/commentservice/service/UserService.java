package com.intuit.commentservice.service;

import com.intuit.commentservice.model.User;
import com.intuit.commentservice.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.h2.engine.UserBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Long addUser(
            @NonNull final String firstName,
            @NonNull final String lastName) {
        final User newUser = User.builder().firstName(firstName)
                .lastName(lastName).build();
        return userRepository.save(newUser).getUserId();
    }

}
