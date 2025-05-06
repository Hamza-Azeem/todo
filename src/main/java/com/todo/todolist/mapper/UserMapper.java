package com.todo.todolist.mapper;

import com.todo.todolist.dto.UserDto;
import com.todo.todolist.entity.User;

public class UserMapper {

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
