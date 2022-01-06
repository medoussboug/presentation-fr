package com.example.cryptotracker.authentication;

import com.example.cryptotracker.domain.User;
import com.example.cryptotracker.domain.UserDTO;
import com.example.cryptotracker.domain.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    private final UserService userService;
    @Autowired
    private final EmailValidator emailValidator;

    public String register(UserDTO user) {
        Boolean isValidEmail = emailValidator.test(user.email);
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return userService.signUpUser(new User(
                user.name,
                user.username,
                user.email,
                user.phoneNumber,
                user.password,
                UserRole.USER
        ));
    }
}
