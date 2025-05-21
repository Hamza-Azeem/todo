package com.todo.todolist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Task {
    @ColumnName("task_id")
    private Integer taskId;
    private String name;
    @ColumnName("user_id")
    private Integer userId;
    @ColumnName("status_id")
    private int statusId;
    
}
