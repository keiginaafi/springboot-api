package com.example.restfulapi.repository;

import com.example.restfulapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Custom query method to find user by email
    Optional<User> findByEmail(String email);
    
    // Custom query method to check if email exists
    boolean existsByEmail(String email);
}