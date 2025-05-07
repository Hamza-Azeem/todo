package com.todo.todolist.service;

import com.todo.todolist.dto.UserDto;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.DuplicateResourceException;
import com.todo.todolist.exception.ResourceNotFoundException;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.model.UserRegistrationRequest;
import com.todo.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.todo.todolist.mapper.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("@permissionService.isAdmin()")
    public List<UserDto> findAllUsers(){
        return repository.findAllUsers().stream()
                .map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    public void addNewUser(UserRegistrationRequest registrationRequest){
        if(repository.userExistsByEmail(registrationRequest.getEmail()) != null){
            throw new DuplicateResourceException("Email is linked to another account.");
        }
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .status("INIT")
                .userType("USER")
                .build();
        repository.saveUser(user);
    }

    @PreAuthorize("@permissionService.isSameUser(#id) || @permissionService.isAdmin()")
    public UserDto findUserById(int id){
        User user = repository.findUserById(id);
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        return toUserDto(user);
    }

    public User findUserByEmail(String email){
        return repository.findUserByEmail(email);
    }

    @PreAuthorize("@permissionService.isSameUser(#id) || @permissionService.isAdmin()")
    public void updateUser(int id, UserDto userDto){
        if(repository.userExistsByEmail(userDto.getEmail()) != null){
            throw new DuplicateResourceException("Email is linked to another account.");
        }
        User user = repository.findUserById(id);
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        boolean updated = false;
        if(userDto.getEmail() != null && !userDto.getEmail().isBlank()){
            user.setEmail(userDto.getEmail());
            updated = true;
        }
        if(userDto.getFirstName() != null && !userDto.getFirstName().isBlank()){
            user.setFirstName(userDto.getFirstName());
            updated = true;
        }
        if(userDto.getLastName() != null && !userDto.getLastName().isBlank()){
            user.setLastName(userDto.getLastName());
            updated = true;
        }
        if(userDto.getPassword() != null && !userDto.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            updated = true;
        }
        if(updated){
            repository.updateUser(user);
        }
    }

    @PreAuthorize("@permissionService.isSameUser(#id) || @permissionService.isAdmin()")
    public void deleteUserById(int id){
        repository.deleteUserById(id);
    }
}
