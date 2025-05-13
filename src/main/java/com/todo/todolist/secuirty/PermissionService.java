package com.todo.todolist.secuirty;

import com.todo.todolist.entity.User;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final UserService userService;
    public boolean isAdmin(){
        return  true;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByEmail(authentication.getName());
//        return user.getUserType().equals("ADMIN") && isActive(user.getStatus());
    }

    public boolean isSameUser(int id){
        return true;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByEmail(authentication.getName());
//        return user.getId() == id && isActive(user.getStatus());
    }

    private boolean isActive(String status){
        return status.equals("ACT");
    }
}
