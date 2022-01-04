package com.example.cryptotracker.presentation;

import com.example.cryptotracker.domain.AuthenticationFacade;
import com.example.cryptotracker.domain.registration.RegistrationService;
import com.example.cryptotracker.domain.user.User;
import com.example.cryptotracker.domain.user.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody UserDTO user) {
        return registrationService.register(user);
    }

}
