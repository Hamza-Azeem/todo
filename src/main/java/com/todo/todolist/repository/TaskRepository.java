package com.todo.todolist.repository;

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
    int addNewTask(@BindBean Task task);

    @SqlUpdate("UPDATE tasks SET name = :name, status_id = :statusId WHERE user_id = :userId")
    @RegisterBeanMapper(Task.class)
    int updateTask(@BindBean Task task);

    @SqlUpdate("DELETE FROM tasks WHERE task_id = :task_id")
    void deleteTaskById(@Bind int task_id);

}
