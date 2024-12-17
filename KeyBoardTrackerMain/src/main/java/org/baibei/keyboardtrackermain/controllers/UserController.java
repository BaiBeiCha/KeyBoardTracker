package org.baibei.keyboardtrackermain.controllers;

import org.baibei.keyboardtrackermain.pojo.user.User;
import org.baibei.keyboardtrackermain.pojo.user.UserResponse;
import org.baibei.keyboardtrackermain.pojo.user.UsersResponse;
import org.baibei.keyboardtrackermain.repositories.UsersRepository;
import org.baibei.keyboardtrackermain.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class UserController {

    private final UsersRepository usersRepository;
    private final UserService userService;

    public UserController(UsersRepository usersRepository,
                          UserService userService) {
        this.usersRepository = usersRepository;
        this.userService = userService;
    }

    @GetMapping("/api/users/{username}")
    public ResponseEntity<UserResponse> user(@PathVariable("username") String username) {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.ok(new UserResponse());
        }

        System.out.println("GET -> " + username);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @GetMapping("/api/users")
    public ResponseEntity<UsersResponse> users() {
        List<User> users = usersRepository.findAll();
        ArrayList<UserResponse> userResponses = new ArrayList<>();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        for (User user : users) {
            userResponses.add(new UserResponse(user));
        }

        System.out.println("GET -> ALL");
        return ResponseEntity.ok(new UsersResponse(userResponses));
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> newUser(@RequestBody UserResponse userResponse) {
        User user = new User();
        user.setUsername(userResponse.getUsername());
        user.setPassword(userResponse.getPassword());
        user.setKeys(userResponse.getKeys());
        user.setWords(userResponse.getWords());
        usersRepository.save(user);

        System.out.println("POST -> " + user.getUsername());
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/api/users/{username}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("username") String username,
                                                   @RequestBody UserResponse userResponse) {
        System.out.println(userResponse.getUsername() + "\t" + userResponse.getPassword());

        User user = usersRepository.findByUsername(username);
        if (user == null) {
            return newUser(userResponse);
        }

        if (userResponse.getPassword() != null) {
            user.setPassword(userResponse.getPassword());
        }
        if (userResponse.getUsername() != null) {
            user.setUsername(userResponse.getUsername());
        }
        if (userResponse.getKeys() != null && !userResponse.getKeys().isEmpty()) {
            user.setKeys(userResponse.getKeys());
        }
        if (userResponse.getWords() != null && !userResponse.getWords().isEmpty()) {
            user.setWords(userResponse.getWords());
        }

        userService.update(user);
        System.out.println("PATCH -> " + user.getUsername());
        return ResponseEntity.ok(new UserResponse(user));
    }

    @DeleteMapping("/api/users/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        System.out.println("DELETE -> " + username);
        usersRepository.delete(user);
    }
}
