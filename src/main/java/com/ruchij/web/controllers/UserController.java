package com.ruchij.web.controllers;

import com.ruchij.daos.user.models.User;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;
import com.ruchij.services.user.UserService;
import com.ruchij.web.requests.CreateUserRequest;
import com.ruchij.web.requests.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody CreateUserRequest createUserRequest) throws ResourceConflictException {
        return userService.create(
            createUserRequest.getEmail(),
            createUserRequest.getPassword(),
            createUserRequest.getFirstName(),
            createUserRequest.getLastName()
        );
    }

    @GetMapping("/id/{userId}")
    public User get(@PathVariable String userId) throws ResourceNotFoundException {
        return userService.getById(userId);
    }

    @PutMapping("/id/{userId}")
    public User update(@PathVariable String userId, @RequestBody UpdateUserRequest updateUserRequest)
        throws ResourceNotFoundException {
        return userService.update(
            userId,
            Optional.ofNullable(updateUserRequest.getEmail()),
            Optional.ofNullable(updateUserRequest.getFirstName()),
            Optional.ofNullable(updateUserRequest.getLastName())
        );
    }
}
