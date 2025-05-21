package com.todo.todolist.entity;


import lombok.*;
import lombok.experimental.Accessors;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class UserToken {
    @ColumnName("user_id")
    private int userId;
    @ColumnName("token_id")
    private String tokenId;
    @ColumnName("issued_at")
    private Instant issuedAt;
    @ColumnName("expires_at")
    private Instant expiresAt;
    @ColumnName("is_valid")
    private Boolean isValid;
    public Boolean getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
