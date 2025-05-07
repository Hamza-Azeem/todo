package com.todo.todolist.service;

import com.todo.todolist.entity.User;
import com.todo.todolist.exception.ResourceNotFoundException;
import com.todo.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    @PreAuthorize("@permissionService.isAdmin()")
    public String changeUserStatus(int type, int id){
        User user = userRepository.findUserById(id);
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        // 0 - init , 1 - active , 2 - sus
        switch (type){
            case 1:
                user.setStatus("ACT");
                break;
            case 2:
                user.setStatus("SUS");
                break;
            default:
                user.setStatus("INIT");
        }
        userRepository.updateUser(user);
        return "User type updated.";
    }

}
