package com.todo.todolist.controller;

import base.BaseResponse;
import com.todo.todolist.dto.PermissionRequestDto;
import com.todo.todolist.service.PermissionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/requests")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionRequestService permissionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse findAllPermissionRequests(){
        return BaseResponse.builder()
                .status(200)
                .message("Requests fetched")
                .data(permissionService.findAllPermissions())
                .build();



    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionRequestDto sendRequest(@PathVariable int userId){
        return permissionService.savePermissionRequest(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePermissionByUserId(@PathVariable int userId){
        permissionService.deletePermissionRequestByUserId(userId);
    }


}
