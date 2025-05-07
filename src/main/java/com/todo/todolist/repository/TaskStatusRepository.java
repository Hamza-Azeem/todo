package com.todo.todolist.repository;

import com.todo.todolist.entity.TaskStatus;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.stereotype.Repository;

@Repository
@RegisterBeanMapper(TaskStatus.class)
public interface TaskStatusRepository {

    @SqlQuery("SELECT * FROM task_status WHERE id = :id LIMIT 1")
    TaskStatus findTaskStatusById(@Bind int id);

}
