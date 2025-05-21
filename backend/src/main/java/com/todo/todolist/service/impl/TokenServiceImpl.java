package com.todo.todolist.service.impl;

import com.todo.todolist.dto.UserDto;
import com.todo.todolist.dto.UserTokenDto;
import com.todo.todolist.dto.admin.UserTasksDto;
import com.todo.todolist.entity.User;
import com.todo.todolist.entity.UserToken;
import com.todo.todolist.exception.InvalidTokenException;
import com.todo.todolist.repository.UserTokenRepository;
import com.todo.todolist.secuirty.JwtUtil;
import com.todo.todolist.service.TaskService;
import com.todo.todolist.service.TokenService;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.todo.todolist.mapper.UserTokenMapper.toUserTokenDto;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserTokenRepository repository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final TaskService taskService;


    @Override
    public UserTasksDto getUserFromToken(String authHeader) {
        String token = authHeader.substring(7);
        UserToken userToken = repository.getUserFromToken(token);
        UserDto userDto = userService.findUserById(userToken.getUserId());

        return UserTasksDto.builder()
                .userDto(userDto)
                .tasks(taskService.findAllTasksByUserId(userDto.getId()))
                .build();
    }

    public String getEmailFromToken(String token){
        return jwtUtil.extractEmail(token);
    }

    @PreAuthorize("@permissionService.isAdmin()")
    @Override
    public int invalidateAllTokensForUser(int userId) {
        return repository.invalidateAllTokensForUserByUserId(userId);
    }

    @Override
    public String saveUserToken(User user) {
        String token = jwtUtil.generateToken(user.getEmail());
        UserToken userToken = UserToken.builder()
                .userId(user.getId())
                .tokenId(token)
                .issuedAt(jwtUtil.extractIssuedAt(token).toInstant())
                .expiresAt(jwtUtil.extractExpiryDate(token).toInstant())
                .isValid(true)
                .build();
        repository.saveUserToken(userToken);
        return token;
    }

    @PreAuthorize("@permissionService.isAdmin()")
    @Override
    public void disableToken(String token) {
        repository.disableUserToken(token);
    }


    @Override
    public List<UserToken> findAllTokensByUserId(int id) {
        return repository.findAllTokensOfUser(id);
    }


//    @PreAuthorize("@permissionService.isAdmin()")
    @Override
    public UserTokenDto findTokenByTokenId(String tokenId) {
        if(tokenId.startsWith("Bearer ")){
            tokenId = tokenId.substring(7);
        }
        return toUserTokenDto(repository.findTokenByTokenId(tokenId));
    }

    @PreAuthorize("@permissionService.isAdmin()")
    @Override
    public void activateToken(String token) {
        repository.activateUserToken(token);
    }

    @Override
    public Boolean isTokenValid(UserToken userToken) {
        if(userToken == null){
            throw new InvalidTokenException("TOKEN NOT FOUND");
        }
        return userToken.getIsValid() && userToken.getExpiresAt().isAfter(Instant.now());
    }

    @Override
    public Boolean isTokenValid(String token) {
        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }
        UserToken userToken = repository.findUserTokenByTokenId(token);
        if (userToken == null){
            throw new InvalidTokenException("TOKEN NOT FOUND");
        }
        boolean valid = userToken.getIsValid() && userToken.getExpiresAt().isAfter(Instant.now());
        if(valid){
            return true;
        }
        disableToken(userToken.getTokenId());
        return false;
    }

    public UserTokenDto findValidUserToken(int userId){
        UserToken userToken = repository.findValidUserToken(userId);
        if(userToken == null){
            return null;
        }
        return toUserTokenDto(userToken);
    }


}
