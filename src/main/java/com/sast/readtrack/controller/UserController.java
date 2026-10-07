package com.sast.readtrack.controller;

import com.sast.readtrack.common.Result;
import com.sast.readtrack.entity.User;
import com.sast.readtrack.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sast.readtrack.dto.UserVO;


import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserVO> list() {
        return userService.listUsers();
    }

    @PostMapping("/user/register")
    public Result<Void> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public Result<Void> login(@RequestBody User user) {
        return userService.login(user);
    }
}
