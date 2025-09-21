package com.example.restfulapi.controller;

import com.example.restfulapi.model.User;
import com.example.restfulapi.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET /api/users - Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/users/{id} - Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST /api/users - Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            // Check if email already exists
            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            
            User newUser = userRepository.save(new User(user.getName(), user.getEmail(), user.getAddress()));
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT /api/users/{id} - Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @Valid @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User existingUser = userData.get();
            
            // Check if email is being changed and if new email already exists
            if (!existingUser.getEmail().equals(user.getEmail()) && 
                userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            
            return new ResponseEntity<>(userRepository.save(existingUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/users/{id} - Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE /api/users - Delete all users
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET /api/users/search?email={email} - Find user by email
    @GetMapping("/search")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> userData = userRepository.findByEmail(email);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}