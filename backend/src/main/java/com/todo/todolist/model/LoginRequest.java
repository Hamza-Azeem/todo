package com.todo.todolist.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
