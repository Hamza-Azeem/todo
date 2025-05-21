package com.todo.todolist.service;

import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.ResourceNotFoundException;
import com.todo.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdminService {
    private final UserRepository userRepository;
    private final TaskService taskService;

    @PreAuthorize("@permissionService.isAdmin()")
    public String changeUserStatus(int type, String identifier){
        User user = null;
        if(identifier.contains("@.")){
            user = userRepository.findUserByEmail(identifier);
        }else{
            user = userRepository.findUserById(Integer.parseInt(identifier));
        }
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        // 0 - init , 1 - active , 2 - sus
        switch (type){
            case 1:
                user.setStatus("ACT");
                break;
            case 2:
                user.setStatus("SUS");
                break;
            default:
                user.setStatus("INIT");
        }
        userRepository.updateUser(user);
        return "User type updated.";
    }

    public void updateUserTask(TaskDto taskDto){
        log.info("START_ADMIN_TASK_UPDATE_SERVICE");
        taskService.adminUpdateTask(taskDto);
        log.info("END_ADMIN_TASK_UPDATE_SERVICE");
    }

    public void deleteUserTask(int taskId){
        taskService.adminDeleteTask(taskId);
    }

}
