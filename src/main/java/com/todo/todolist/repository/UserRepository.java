package com.todo.todolist.repository;

import com.todo.todolist.entity.User;
import com.todo.todolist.sql.UserSQL;
import lombok.RequiredArgsConstructor;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final DataSource dataSource;

    Connection connection = null;
    Handle handler = null;
    UserSQL userSQL = null;

    @PostConstruct
    public void init(){
        connection = DataSourceUtils.getConnection(dataSource);
        handler = DBI.open(connection);
        UserSQL userSQL = handler.attach(UserSQL.class);
    }

    public List<User> findAllUsers(){
        return userSQL.findAllUsers();
    }

    public int addUser(User user){
        return userSQL.saveUser(user);
    }

    public int updateUser(User user){
        return userSQL.updateUser(user);
    }

    public int deleteUserById(int id){
        return userSQL.deleteUserById(id);
    }
    public int userExistsByEmail(String email){
        return userSQL.userExistsByEmail(email);
    }
    public User findUserById(int id){
        return userSQL.findUserById(id);
    }

}
