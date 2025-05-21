package com.todo.todolist.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class UserRegistrationRequest {
    String firstName;
    String lastName;
    String email;
    String password;
}
