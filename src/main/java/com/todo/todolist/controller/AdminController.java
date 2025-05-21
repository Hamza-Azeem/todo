package com.todo.todolist.controller;

import base.BaseResponse;
import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.service.AdminService;
import com.todo.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
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

    @PutMapping("/update-task")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse updateUserTask(@RequestBody TaskDto taskDto){
        log.info("START_API-ADMIN_TASK_UPDATE");
        adminService.updateUserTask(taskDto);
        log.info("END_API-ADMIN_TASK_UPDATE");
        return new BaseResponse(200,"TASK_UPDATED_BY_ADMIN", null);
    }

    @DeleteMapping("/task/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse deleteUserTask(@PathVariable int  taskId){
        adminService.deleteUserTask(taskId);
        return new BaseResponse(204,"TASK_DELETED_BY_ADMIN", null);
    }

}
