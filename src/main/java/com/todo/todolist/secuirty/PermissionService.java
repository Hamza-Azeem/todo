package com.todo.todolist.secuirty;

import com.todo.todolist.entity.User;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PermissionService {

    private final UserService userService;
    public boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        log.info("ADMIN_ENDPOINT_ACCESSED_BY {} THAT_HAS_ROLE {}", user.getEmail(), user.getUserType());
        return user.getUserType().equals("ADMIN") && isActive(user.getStatus());
    }

    public boolean isSameUser(int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        return user.getId() == id && isActive(user.getStatus());
    }

    private boolean isActive(String status){
        return status.equals("ACT");
    }
}
