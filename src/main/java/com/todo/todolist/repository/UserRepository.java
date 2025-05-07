package com.todo.todolist.repository;

import com.todo.todolist.entity.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RegisterBeanMapper(User.class)
public interface UserRepository {
    
    @SqlQuery("SELECT * FROM users")
    @RegisterBeanMapper(User.class)
    List<User> findAllUsers();

    @SqlUpdate("INSERT INTO users(first_name, last_name, email, password, status, user_type)" +
    " values(:firstName, :lastName, :email, :password, :status, :userType)")
    @GetGeneratedKeys
    int saveUser(@BindBean User user);

    @SqlQuery("SELECT * FROM users WHERE id=:id")
    @RegisterBeanMapper(User.class)
    User findUserById(@Bind int id);
    @SqlUpdate("UPDATE users SET first_name = :firstName, last_name = :lastName, email = :email," +
    " password = :password, status = :status, user_type = :userType WHERE id = :id")
    int updateUser(@BindBean User user);

    @SqlUpdate("UPDATE users SET status = 'SUS' WHERE id=:id")
    int deleteUserById(@Bind int id);

    @SqlQuery("SELECT 1 FROM users WHERE email = :email LIMIT 1")
    Integer userExistsByEmail(@Bind String email);

    @SqlQuery("SELECT * FROM users WHERE email = :email LIMIT 1")
    User findUserByEmail(@Bind String email);


}
