package com.intuit.commentservice.controller;

import com.intuit.commentservice.service.UserService;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.intuit.commentservice.util.ApiConstants.*;

@RestController
@Validated
@RequestMapping(value = "api/v1/intuit/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(
            name = API_NAME_POST_COMMENT,
            value = POST_COMMENT)
    ResponseEntity<?> createUser(
            @NotBlank @RequestParam(value = REQUEST_FIRST_NAME, required = true) final String firstName,
            @Nullable @RequestParam(value = REQUEST_SECOND_NAME, defaultValue = "") final String secondName) {
        Long userId = userService.addUser(firstName, secondName);
        return ResponseEntity.noContent()
                .header(HEADER_USER_ID, String.valueOf(userId)).build();
    }

}
