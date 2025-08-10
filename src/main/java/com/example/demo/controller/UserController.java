package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserAlreadyExistsException;
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
    public void registerUser(@RequestBody User user)throws UserAlreadyExistsException {
        userService.addNewUser(user);

    }

    @DeleteMapping
    public void deleteUser(@RequestBody Long Id) throws UserNotFoundException{
        userService.deleteUsrer(Id);

    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable("userId") Long id, @RequestBody User user)throws UserNotFoundException {

        userService.updateuser(id, user);
    }
}
