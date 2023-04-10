package com.arato.Mezashi.User;

import com.arato.Mezashi.User.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class UserResource {
    private UserRepository userRepository;
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable long userId) {
        return _findUserById(userId);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        this.userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    private User _findUserById(long userId) {
        var user = this.userRepository.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("user with id=" + userId + " is not found");
        return user.get();
    }
}
