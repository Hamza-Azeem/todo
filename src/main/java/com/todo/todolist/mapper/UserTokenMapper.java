package com.todo.todolist.mapper;

import com.todo.todolist.dto.UserTokenDto;
import com.todo.todolist.entity.UserToken;

public class UserTokenMapper {

    public static UserTokenDto toUserTokenDto(UserToken userToken){
        return UserTokenDto.builder()
                .tokenId(userToken.getTokenId())
                .userId(userToken.getUserId())
                .isValid(userToken.getIsValid())
                .build();
    }
}
