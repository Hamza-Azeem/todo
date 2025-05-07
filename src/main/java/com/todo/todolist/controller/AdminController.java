package com.todo.todolist.controller;

import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.service.AdminService;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final TaskService taskService;

    @GetMapping("/change-type")
    public ResponseEntity<String> changeUserType(@RequestParam(required = true) int id
            , @RequestParam(defaultValue = "0") int type){
        return ResponseEntity.ok(adminService.changeUserStatus(type, id));
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<List<TaskDto>> findAllTasks(){
        return ResponseEntity.ok(taskService.findAllTasks());
    }
}
