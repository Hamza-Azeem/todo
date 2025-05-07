package com.todo.todolist.mapper;

import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.entity.Task;

public class TaskMapper {

    public static TaskDto toTaskDto(Task task){
        return TaskDto.builder()
                .id(task.getTaskId())
                .name(task.getName())
                .status(task.getStatusId())
                .build();
    }
}
