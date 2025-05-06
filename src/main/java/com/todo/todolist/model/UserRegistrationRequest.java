package com.todo.todolist.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserRegistrationRequest {
    String firstName;
    String lastName;
    String email;
    String password;
}
