package com.todo.todolist.service;

import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.dto.UserDto;
import com.todo.todolist.entity.Task;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.ResourceNotFoundException;
import com.todo.todolist.mapper.TaskMapper;
import com.todo.todolist.model.TaskCreationRequest;
import com.todo.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.todo.todolist.mapper.TaskMapper.toTaskDto;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final UserService userService;

    public void createTask(int userId, TaskCreationRequest request){
        UserDto user = userService.findUserById(userId);
        // if(!user.getStatus().equals("ACT")){
        //     throw new AccessDeniedException("ACCESS DENIED");
        // }
        Task task = Task.builder()
                .name(request.getName())
                .statusId(request.getStatus())
                .userId(user.getId())
                .build();
        repository.addNewTask(task);
    }

    @PreAuthorize("@permissionService.isSameUser(#userId)")
    public void updateTask(int userId, int taskId, TaskDto taskDto){
        Task task = repository.findTaskById(taskId);
        if(task == null){
            throw new ResourceNotFoundException("Task not found");
        }
        if(task.getUserId() != userId){
            throw new AccessDeniedException("ACCESS_DENIED");

        }
        boolean updated = false;
        if(taskDto.getName() != null && !taskDto.getName().isBlank()){
            task.setName(taskDto.getName());
            updated = true;
        }
        if(taskDto.getStatus() != null && taskDto.getStatus() > 0 && taskDto.getStatus() < 4){
            task.setStatusId(taskDto.getStatus());
            updated = true;
        }
        if(updated){
            repository.updateTask(task);
        }
    }
    @PreAuthorize("@permissionService.isSameUser(#userId) || @permissionService.isAdmin()")
    public TaskDto findTaskById(int userId, int taskId){
        Task task = repository.findTaskById(taskId);
        if(task == null){
            throw new ResourceNotFoundException("Task not found");
        }
        return toTaskDto(task);
    }

    @PreAuthorize("@permissionService.isSameUser(#userId) || @permissionService.isAdmin()")
    public void deleteTaskById(int userId, int taskId){
        repository.deleteTaskById(taskId);
    }

    @PreAuthorize("@permissionService.isSameUser(#userId) || @permissionService.isAdmin()")
    public List<TaskDto> findAllTasksByUserId(int userId){
        return repository.findTasksByUserId(userId)
                .stream().map(TaskMapper::toTaskDto).collect(Collectors.toList());
    }

    @PreAuthorize("@permissionService.isAdmin()")
    public List<TaskDto> findAllTasks(){
        return repository.findAllTasks()
                .stream().map(TaskMapper::toTaskDto).collect(Collectors.toList());
    }


}
