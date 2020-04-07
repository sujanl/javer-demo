package com.sujan.javersdemo.controller;

import com.sujan.javersdemo.dto.UserInfo;
import com.sujan.javersdemo.entities.Users;
import com.sujan.javersdemo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(@RequestBody UserInfo userInfo) {
       return userService.create(userInfo);
    }

    @PutMapping
    public String update(@RequestBody UserInfo userInfo) {
        return userService.update(userInfo);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        userService.delete(id);
        return "deleted";
    }

    @GetMapping("/list")
    public List<Users> getAll() {
        return userService.findAll();
    }
}
