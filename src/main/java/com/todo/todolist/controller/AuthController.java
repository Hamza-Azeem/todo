package com.todo.todolist.controller;

import com.todo.todolist.model.UserRegistrationRequest;
import com.todo.todolist.secuirty.AuthenticationService;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    private ResponseEntity<String> addUser(@RequestBody UserRegistrationRequest registrationRequest){
        userService.addNewUser(registrationRequest);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
    }

}
