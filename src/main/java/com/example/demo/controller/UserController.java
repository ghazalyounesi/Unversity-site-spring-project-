package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getuser();
    }

    @PostMapping
    public void registerUser(@RequestBody User user) {
        userService.addNewUser(user);

    }

    @DeleteMapping
    public void deleteUser(@RequestBody Long Id) {
        userService.deleteUsrer(Id);

    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long id, @RequestBody User user) {

        userService.updateuser(id, user);
    }
}
