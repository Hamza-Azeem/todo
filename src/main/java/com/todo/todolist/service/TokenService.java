package com.todo.todolist.service;


import com.todo.todolist.dto.UserTokenDto;
import com.todo.todolist.dto.admin.UserTasksDto;
import com.todo.todolist.entity.User;
import com.todo.todolist.entity.UserToken;

import java.util.List;

public interface TokenService {
    UserTasksDto getUserFromToken(String token);
    String saveUserToken(User user);
    void disableToken(String token);
    List<UserToken> findAllTokensByUserId(int userId);
    UserTokenDto findTokenByTokenId(String tokenId);
    void activateToken(String token);
    Boolean isTokenValid(UserToken token);
    Boolean isTokenValid(String  token);
    String getEmailFromToken(String token);
    UserTokenDto findValidUserToken(int userId);
    int invalidateAllTokensForUser(int userId);

}
