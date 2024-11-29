package com.obj.meeting.controller;

import com.obj.meeting.entity.UserEntity;
import com.obj.meeting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{username}")
    public UserEntity getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/list")
    public List<UserEntity> getUserList() {
        return userService.getUserList();
    }

    @PostMapping("/save")
    public String saveUser(UserEntity user) {
        return userService.save(user);
    }
}
