package com.todo.todolist.entity;

import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PermissionRequest {
    @ColumnName("request_id")
    private Integer requestId;
    @ColumnName("user_id")
    private Integer userId;
}
