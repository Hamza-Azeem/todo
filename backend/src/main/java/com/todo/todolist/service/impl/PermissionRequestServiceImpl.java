package com.todo.todolist.service.impl;

import com.todo.todolist.dto.PermissionRequestDto;
import com.todo.todolist.entity.PermissionRequest;
import com.todo.todolist.mapper.PermissionRequestMapper;
import com.todo.todolist.repository.PermissionRequestRepository;
import com.todo.todolist.service.PermissionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.todo.todolist.mapper.PermissionRequestMapper.toPermissionRequestDto;

@Service
@RequiredArgsConstructor
public class PermissionRequestServiceImpl implements PermissionRequestService {
    private final PermissionRequestRepository repository;

    @PreAuthorize("@permissionService.isAdmin()")
    @Override
    public List<PermissionRequestDto> findAllPermissions() {
        return repository.findAllRequests()
                .stream().map(PermissionRequestMapper::toPermissionRequestDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("@permissionService.isAdmin()")
    @Override
    public PermissionRequestDto findPermissionByUserId(int userId) {
        return toPermissionRequestDto(repository.findRequestByUserId(userId));
    }

    @PreAuthorize("@permissionService.isAdmin()")
    @Override
    public void deletePermissionRequestByUserId(int userId) {
        repository.deleteRequestByUserId(userId);
    }

    @Override
    public PermissionRequestDto savePermissionRequest(int userId) {
        PermissionRequest permissionRequest = repository.findRequestByUserId(userId);
        // don't send more than one request by user
        if(permissionRequest == null){
            return toPermissionRequestDto(repository.sendPermissionRequest(userId));
        }
        return toPermissionRequestDto(permissionRequest);
    }
}
