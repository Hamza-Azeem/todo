package com.todo.todolist.dto.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.todo.todolist.dto.TaskDto;
import com.todo.todolist.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTasksDto {
    UserDto userDto;
    List<TaskDto> tasks;
}
