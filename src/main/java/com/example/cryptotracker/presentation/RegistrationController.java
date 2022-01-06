package com.example.cryptotracker.presentation;

import com.example.cryptotracker.authentication.RegistrationService;
import com.example.cryptotracker.domain.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
