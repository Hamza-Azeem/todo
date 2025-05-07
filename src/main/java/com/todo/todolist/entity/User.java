package com.todo.todolist.entity;

import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    @ColumnName("first_name")
    private String firstName;
    @ColumnName("last_name")
    private String lastName;
    private String email;
    private String password;
    private String status;
    @ColumnName("user_type")
    private String userType;

}
