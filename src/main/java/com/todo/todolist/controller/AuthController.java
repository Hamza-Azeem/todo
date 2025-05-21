package com.todo.todolist.controller;

import com.todo.todolist.model.LoginRequest;
import com.todo.todolist.model.UserRegistrationRequest;
import com.todo.todolist.secuirty.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody UserRegistrationRequest registrationRequest){
        authenticationService.addNewUser(registrationRequest);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest){
        log.info("Start login API with userEmail {}",loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate( // Remove from here
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        log.info("End login API");

        return ResponseEntity.ok(authenticationService.loginUser(authentication));
    }

}
