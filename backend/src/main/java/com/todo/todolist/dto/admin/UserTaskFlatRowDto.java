package com.todo.todolist.dto.admin;

import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class UserTaskFlatRowDto {
    // User fields
    @ColumnName("user_id")
    private int userId;
    @ColumnName("first_name")
    private String firstName;
    @ColumnName("last_name")
    private String lastName;
    private String email;
    @ColumnName("user_type")
    private String userType;

    // Task fields
    @ColumnName("task_id")
    private Integer taskId;
    private String name;
    @ColumnName("status_id")
    private Integer statusId;
}
