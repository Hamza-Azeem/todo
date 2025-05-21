package com.todo.todolist.service;

import com.todo.todolist.dto.PermissionRequestDto;

import java.util.List;

public interface PermissionRequestService {
    List<PermissionRequestDto> findAllPermissions();
    PermissionRequestDto findPermissionByUserId(int userId);
    void deletePermissionRequestByUserId(int userId);
    PermissionRequestDto savePermissionRequest(int userId);
}
