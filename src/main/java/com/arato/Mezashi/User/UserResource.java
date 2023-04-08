package com.arato.Mezashi.User;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserResource {
    private UserRepository userRepository;
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable long userId) {

        return new User("arato", "password");
    }

    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user) {
        this.userRepository.save(user);
    }
}
