package com.todo.todolist.controller;

import base.BaseResponse;
import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.model.TaskCreationRequest;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@Log4j2
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
    @ResponseStatus(HttpStatus.CREATED)
    private TaskDto addTask(@RequestBody TaskCreationRequest request){
        return taskService.createTask(request);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    private void updateTask(@PathVariable int userId, @RequestBody TaskDto taskDto){
        taskService.updateTask(userId, taskDto.getId(), taskDto);
    }

    @DeleteMapping("/{userId}/{id}")
    private ResponseEntity<Void> deleteTaskById(@PathVariable int userId, @PathVariable int id){
        taskService.deleteTaskById(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    private BaseResponse findAllUsersAndTasks(){
        log.info("START_TASKS_USERS_API");
        BaseResponse baseResponse = new BaseResponse(200, "USERS_TASKS_RETRIEVED_SUCCESSFULLY",
                taskService.findAllUsersWithTasks());
        log.info("END_TASKS_USERS_API");
        return baseResponse;
    }
}
