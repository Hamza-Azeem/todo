package com.todo.todolist.secuirty;

import com.todo.todolist.controller.AuthController;
import com.todo.todolist.dto.UserTokenDto;
import com.todo.todolist.entity.User;
import com.todo.todolist.exception.DuplicateResourceException;
import com.todo.todolist.model.UserRegistrationRequest;
import com.todo.todolist.service.TokenService;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles().build();
    }

    public String loginUser(Authentication authentication) {
        logger.info("Start Service Login");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByEmail(userDetails.getUsername());
        logger.info("Finished Finding user");
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        logger.info("Start Finding Valid Token");
        UserTokenDto token = tokenService.findValidUserToken(user.getId());

        if (token == null) {
            logger.info("Generating New Token");
            return tokenService.saveUserToken(user);  // Returns a new tokenId
        }
        logger.info("End Finding Valid Token");
        logger.info("End Login Service");
        return token.getTokenId();  // Return existing valid token's ID
    }

    public void addNewUser(UserRegistrationRequest registrationRequest) {
        if (userService.userExistsByEmail(registrationRequest.getEmail())) {
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
        userService.saveUser(user);
    }

}
