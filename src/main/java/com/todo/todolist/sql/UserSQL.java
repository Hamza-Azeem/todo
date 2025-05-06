package com.todo.todolist.sql;

import com.todo.todolist.entity.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import java.util.List;

public interface UserSQL {
    
    @SqlQuery("SELECT * FROM users")
    @RegisterBeanMapper(User.class)
    List<User> findAllUsers();

    @SqlUpdate("INSERT INTO users(first_name, last_name, email, password, status, user_type)" + 
    " value(:firstName, :lastName, :email, :password, :status, :userType)")
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
    int userExistsByEmail(@Bind String email);


}
