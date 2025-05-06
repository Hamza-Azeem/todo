package com.todo.todolist.service;

import com.todo.todolist.dto.UserDto;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.DuplicateResourceException;
import com.todo.todolist.exception.ResourceNotFoundException;
import com.todo.todolist.mapper.UserMapper;
import com.todo.todolist.model.UserRegistrationRequest;
import com.todo.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.todo.todolist.mapper.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<UserDto> findAllUsers(){
        return repository.findAllUsers().stream()
                .map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    public int addNewUser(UserRegistrationRequest registrationRequest){
        if(repository.userExistsByEmail(registrationRequest.getEmail()) >= 1){
            throw new DuplicateResourceException("Email is linked to another account.");
        }
        User user = User.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .status("INIT")
                .userType("USER")
                .build();
        return repository.addUser(user);
    }

    public UserDto findUserById(int id){
        User user = repository.findUserById(id);
        if(user == null){
            throw new ResourceNotFoundException("User not found");
        }
        return toUserDto(user);
    }

    public int updateUser(int id, UserDto userDto){
        if(repository.userExistsByEmail(userDto.getEmail()) >= 1){
            throw new DuplicateResourceException("Email is linked to another account.");
        }
        User user = repository.findUserById(id);
        if(userDto.getEmail() != null && !userDto.getEmail().isBlank()){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getFirstName() != null && !userDto.getFirstName().isBlank()){
            user.setFirstName(userDto.getFirstName());
        }
        if(userDto.getLastName() != null && !userDto.getLastName().isBlank()){
            user.setLastName(userDto.getLastName());
        }
        return repository.updateUser(user);
    }

    public int deleteUserById(int id){
        return repository.deleteUserById(id);
    }
}
