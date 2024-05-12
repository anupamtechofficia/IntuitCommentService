package com.intuit.commentservice;

import com.intuit.commentservice.exceptions.RestResponseEntityExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RestResponseEntityExceptionHandler.class)
public class IntuitCommentServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(IntuitCommentServiceApplication.class, args);
    }

}
