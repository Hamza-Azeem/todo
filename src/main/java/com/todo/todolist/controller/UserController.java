package com.todo.todolist.controller;

import com.todo.todolist.dto.UserDto;
import com.todo.todolist.model.UserRegistrationRequest;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    private ResponseEntity<List<UserDto>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDto> findUserById(@PathVariable int id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping
    private ResponseEntity<String> addUser(@RequestBody UserRegistrationRequest registrationRequest){
        userService.addNewUser(registrationRequest);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserDto userDto){
        userService.updateUser(id, userDto);
        return new ResponseEntity<>("User updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteUserById(@PathVariable int id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
