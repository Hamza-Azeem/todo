package com.todo.todolist.service;

import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.dto.UserDto;
import com.todo.todolist.dto.admin.UserTaskFlatRowDto;
import com.todo.todolist.entity.Task;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.ResourceNotFoundException;
import com.todo.todolist.mapper.TaskMapper;
import com.todo.todolist.model.TaskCreationRequest;
import com.todo.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.todo.todolist.mapper.TaskMapper.toTaskDto;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskService {
    private final TaskRepository repository;
    private final UserService userService;

    public TaskDto createTask(TaskCreationRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        if(!user.getStatus().equals("ACT")){
            throw new AccessDeniedException("ACCESS DENIED");
        }
        Task task = Task.builder()
                .name(request.getName())
                .statusId(request.getStatus())
                .userId(user.getId())
                .build();
       return toTaskDto(repository.addNewTask(task));
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
        if(taskDto.getName() != null && !taskDto.getName().isBlank() && !taskDto.getName().equals(task.getName())){
            task.setName(taskDto.getName());
            updated = true;
        }
        if(taskDto.getStatus() != null && taskDto.getStatus() > 0 && taskDto.getStatus() < 4 && taskDto.getStatus() != task.getStatusId()){
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

    @PreAuthorize("@permissionService.isAdmin()")
    public Map<String, List<TaskDto>> findAllUsersWithTasks(){
        log.info("START_USERS_TASKS_SERVICE");
        List<UserTaskFlatRowDto> userTaskFlatRowDtos = repository.selectAllUsersWithTheirTasks();
        log.info("END_USERS_TASKS_SERVICE - FOUND '{}' RESULTS", userTaskFlatRowDtos.size());
        Map<String, List<TaskDto>> userTaskMap = new HashMap<>();
        for(UserTaskFlatRowDto userTask: userTaskFlatRowDtos){
            if (userTaskMap.containsKey(userTask.getEmail())) {
                List<TaskDto> temp = userTaskMap.get(userTask.getEmail());
                if (userTask.getTaskId() != null) { // Only add valid tasks
                    temp.add(toTaskDto(userTask));
                }
            } else {
                List<TaskDto> tasks = new ArrayList<>();
                if (userTask.getTaskId() != null) {
                    tasks.add(toTaskDto(userTask));
                }
                userTaskMap.put(userTask.getEmail(), tasks);
            }

        }
        return userTaskMap;
    }

    @PreAuthorize("@permissionService.isAdmin()")
    public void adminUpdateTask(TaskDto taskDto){
        log.info("START_TASK_SERVICE_ADMIN_UPDATE");
        log.info("FINDING_THE_TASK");
        Task task = repository.findTaskById(taskDto.getId());
        if(task == null){
            throw new ResourceNotFoundException("Task not found");
        }
        log.info("FOUND_TASK");
        task.setStatusId(taskDto.getStatus());
        task.setName(taskDto.getName());
        log.info("END_TASK_SERVICE");
        repository.adminUpdateTask(task);
    }

    @PreAuthorize("@permissionService.isAdmin()")
    public void adminDeleteTask(int taskId){
        repository.adminDeleteTask(taskId);
    }


}
