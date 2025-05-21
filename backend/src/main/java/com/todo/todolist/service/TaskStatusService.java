package com.todo.todolist.service;

import com.todo.todolist.entity.TaskStatus;
import com.todo.todolist.repository.TaskStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskStatusService {
    private final TaskStatusRepository repository;

    public TaskStatus findTaskStatusById(int id){
        return repository.findTaskStatusById(id);
    }

}
