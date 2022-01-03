package com.example.cryptotracker.domain.registration;

import com.example.cryptotracker.domain.user.User;
import com.example.cryptotracker.domain.user.UserDTO;
import com.example.cryptotracker.domain.user.UserRole;
import com.example.cryptotracker.domain.user.UserService;
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
