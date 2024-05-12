package com.intuit.commentservice.repository;

import com.intuit.commentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface UserRepository extends JpaRepository<User, Long> {

}