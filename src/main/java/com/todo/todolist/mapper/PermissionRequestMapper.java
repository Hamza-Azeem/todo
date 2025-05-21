package com.todo.todolist.mapper;

import com.todo.todolist.dto.PermissionRequestDto;
import com.todo.todolist.entity.PermissionRequest;

public class PermissionRequestMapper {

    public static PermissionRequestDto toPermissionRequestDto(PermissionRequest request){
        return PermissionRequestDto
                .builder()
                .requestId(request.getRequestId())
                .userId(request.getUserId())
                .build();
    }
}
