package com.todo.todolist.controller;

import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.model.TaskCreationRequest;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{userId}/{id}")
    private ResponseEntity<TaskDto> findTaskById(@PathVariable int userId, @PathVariable int id){
        return ResponseEntity.ok(taskService.findTaskById(userId, id));
    }

    @GetMapping("/{userId}")
    private ResponseEntity<List<TaskDto>> findAllUserTasks(@PathVariable int userId){
        return ResponseEntity.ok(taskService.findAllTasksByUserId(userId));
    }

    @PostMapping
    private ResponseEntity<Void> addTask(@RequestBody TaskCreationRequest request){
        taskService.createTask(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/{id}")
    private ResponseEntity<Void> updateTask(@PathVariable int userId, @PathVariable int id, @RequestBody TaskDto taskDto){
        taskService.updateTask(userId, id, taskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{id}")
    private ResponseEntity<Void> deleteTaskById(@PathVariable int userId, @PathVariable int id){
        taskService.deleteTaskById(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
