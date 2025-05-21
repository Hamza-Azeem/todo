package com.todo.todolist.repository;

import com.todo.todolist.entity.PermissionRequest;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RegisterBeanMapper(PermissionRequest.class)
public interface PermissionRequestRepository {

    @SqlQuery("SELECT * FROM requests")
    @RegisterBeanMapper(PermissionRequest.class)
    List<PermissionRequest> findAllRequests();

    @SqlQuery("SELECT * FROM requests WHERE user_id = :userId")
    @RegisterBeanMapper(PermissionRequest.class)
    PermissionRequest findRequestByUserId(@Bind int userId);

    @SqlUpdate("INSERT INTO requests(user_id) VALUES(:userId)")
    @RegisterBeanMapper(PermissionRequest.class)
    @GetGeneratedKeys
    PermissionRequest sendPermissionRequest(@Bind int userId);


    @SqlUpdate("DELETE FROM requests WHERE user_id = :userId")
    @RegisterBeanMapper(PermissionRequest.class)
    void deleteRequestByUserId(@Bind int userId);
}
