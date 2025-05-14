package com.todo.todolist.secuirty;

import com.todo.todolist.entity.User;
import com.todo.todolist.entity.UserToken;
import com.todo.todolist.exception.DuplicateResourceException;
import com.todo.todolist.model.UserRegistrationRequest;
import com.todo.todolist.service.TokenService;
import com.todo.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByEmail(userDetails.getUsername());

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserToken token = getValidTokenForUser(user);

        if (token == null) {
            return tokenService.saveUserToken(user);  // Returns a new tokenId
        }

        return token.getTokenId();  // Return existing valid token's ID
    }

    private UserToken getValidTokenForUser(User user) {
        return tokenService.findAllTokensByUserId(user.getId())
                .stream()
                .filter(tokenService::isTokenValid)
                .findFirst()
                .orElse(null);
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
