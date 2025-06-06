package com.todo.todolist.controller;

import com.todo.todolist.dto.UserTokenDto;
import com.todo.todolist.dto.admin.UserTasksDto;
import com.todo.todolist.entity.User;
import com.todo.todolist.entity.UserToken;
import com.todo.todolist.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;


    @GetMapping("/{id}")
    public List<UserToken> getAllUserTokens(@PathVariable int id){
        return tokenService.findAllTokensByUserId(id);
    }

    // returns both user and his tasks
    @GetMapping("/user")
    @ResponseStatus(code = HttpStatus.OK)
    public UserTasksDto findUserByToken(@RequestHeader("Authorization") String authHeader){
        return tokenService.getUserFromToken(authHeader);
    }

    // returns only the user token entity
    // todo: Change entity to DTO
    @GetMapping("/user-token")
    @ResponseStatus(code = HttpStatus.OK)
    public UserTokenDto findUserTokenByToken(@RequestHeader("Authorization") String authHeader){
        return tokenService.findTokenByTokenId(authHeader);
    }


    @GetMapping("/disable")
    @ResponseStatus(code = HttpStatus.OK)
    public String disableUserToken(@RequestBody UserTokenDto userTokenDto){
        tokenService.disableToken(userTokenDto.getTokenId());
        return "TOKEN_DEACTIVATED";
    }

    @GetMapping("/enable")
    @ResponseStatus(code = HttpStatus.OK)
    public String enableUserToken(@RequestBody UserTokenDto userTokenDto){
        tokenService.activateToken(userTokenDto.getTokenId());
        return "TOKEN_ACTIVATED";
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public boolean checkTokenValidity(@RequestHeader("Authorization") String authHeader){
        return tokenService.isTokenValid(authHeader);
    }



}
