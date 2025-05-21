package com.todo.todolist.repository;

import com.todo.todolist.dto.admin.UserTaskFlatRowDto;
import com.todo.todolist.dto.admin.UserTasksDto;
import com.todo.todolist.entity.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RegisterBeanMapper(Task.class)
public interface TaskRepository {

    @SqlQuery("SELECT * FROM tasks")
    @RegisterBeanMapper(Task.class)
    List<Task> findAllTasks();

    @SqlQuery("SELECT * FROM tasks WHERE user_id = :user_id")
    @RegisterBeanMapper(Task.class)
    List<Task> findTasksByUserId(@Bind int user_id);

    @SqlQuery("SELECT * FROM tasks WHERE task_id = :task_id LIMIT 1")
    @RegisterBeanMapper(Task.class)
    Task findTaskById(@Bind int task_id);

    @SqlUpdate("INSERT INTO tasks(name, user_id, status_id) values(:name, :userId, :statusId)")
    @GetGeneratedKeys
    @RegisterBeanMapper(Task.class)
    Task addNewTask(@BindBean Task task);

    @SqlUpdate("UPDATE tasks SET name = :name, status_id = :statusId WHERE user_id = :userId AND task_id = :taskId")
    @RegisterBeanMapper(Task.class)
    Integer updateTask(@BindBean Task task);

    @SqlUpdate("DELETE FROM tasks WHERE task_id = :task_id")
    void deleteTaskById(@Bind int task_id);

    @SqlQuery("select * from tasks RIGHT JOIN users ON tasks.user_id = users.id")
    @RegisterBeanMapper(UserTaskFlatRowDto.class)
    List<UserTaskFlatRowDto> selectAllUsersWithTheirTasks();

    @SqlUpdate("UPDATE tasks SET name = :name, status_id = :statusId WHERE task_id = :taskId")
    @RegisterBeanMapper(Task.class)
    Integer adminUpdateTask(@BindBean Task task);

    @SqlUpdate("DELETE FROM tasks WHERE task_id = :taskId")
    @RegisterBeanMapper(Task.class)
    Integer adminDeleteTask(@Bind int taskId);

}
