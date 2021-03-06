package com.example.cryptotracker.authentication;

import com.example.cryptotracker.domain.User;
import com.example.cryptotracker.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND = "User with %s not found";
    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }

    public String signUpUser(User user) {
        boolean isUserEmailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean isUserUsernameExists = userRepository.findByUsername(user.getUsername()).isPresent();
        if (isUserEmailExists) {
            throw new IllegalStateException("Email already exists");
        }
        if (isUserUsernameExists) {
            throw new IllegalStateException("username already exists");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "signed";
    }

    public void notifyUser() {
        List<User> users = userRepository.findAll();

    }


}
