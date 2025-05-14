package com.todo.todolist.repository;

import com.todo.todolist.entity.UserToken;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RegisterBeanMapper(UserToken.class)
public interface UserTokenRepository {
    @SqlQuery("SELECT * FROM user_tokens WHERE token_id = :tokenId")
    @RegisterBeanMapper(UserToken.class)
    UserToken getUserFromToken(@Bind String tokenId);

    @SqlUpdate("INSERT INTO user_tokens(user_id, token_id, issued_at, expires_at, is_valid) VALUES(:userId, :tokenId, :issuedAt, :expiresAt, :isValid)")
    @RegisterBeanMapper(UserToken.class)
    Integer saveUserToken(@BindBean UserToken userToken);

    @SqlUpdate("UPDATE user_tokens SET is_valid = false WHERE token_id = :tokenId")
    @RegisterBeanMapper(UserToken.class)
    Integer disableUserToken(@Bind String tokenId);

    @SqlUpdate("UPDATE user_tokens SET is_valid = true WHERE token_id = :tokenId")
    @RegisterBeanMapper(UserToken.class)
    Integer activateUserToken(@Bind String tokenId);

    @SqlQuery("SELECT * FROM user_tokens WHERE user_id = :userId")
    @RegisterBeanMapper(UserToken.class)
    List<UserToken> findAllTokensOfUser(@Bind int userId);

    @SqlQuery("SELECT * FROM user_tokens WHERE token_id = :tokenId")
    @RegisterBeanMapper(UserToken.class)
    UserToken findTokenByTokenId(@Bind String tokenId);

    @SqlQuery("SELECT * FROM user_tokens WHERE token_id = :tokenId")
    @RegisterBeanMapper(UserToken.class)
    UserToken findUserTokenByTokenId(@Bind String tokenId);

    @SqlUpdate("UPDATE user_tokens SET is_valid = false WHERE user_id = :userId")
    @RegisterBeanMapper(UserToken.class)
    Integer invalidateAllTokensForUserByUserId(@Bind int userId);


}
