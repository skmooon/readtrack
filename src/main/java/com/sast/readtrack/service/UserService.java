package com.sast.readtrack.service;

import com.sast.readtrack.common.Result;
import com.sast.readtrack.entity.User;
import com.sast.readtrack.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.sast.readtrack.dto.UserVO;

import java.util.ArrayList;


import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserVO> listUsers() {
        List<User> users = userMapper.findAll();
        List<UserVO> result = new ArrayList<>();
        for (User user : users) {
            UserVO vo = new UserVO();
            vo.setId(user.getId());
            vo.setUsername(user.getUsername());
            vo.setCreatedAt(user.getCreatedAt());
            result.add(vo);
        }
        return result;
    }
    
    public Result<Void> register(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return Result.fail("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return Result.fail("密码不能为空");
        }
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return Result.fail("用户名已存在");
        } else {
            userMapper.insert(user);
            return Result.success();
        }
    }

    public Result<Void> login(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return Result.fail("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return Result.fail("密码不能为空");
        }

        User found = userMapper.findByUsername(user.getUsername());

        if (found == null || !found.getPassword().equals(user.getPassword())) {
            return Result.fail("用户名或密码错误");
        } else {
            return Result.success();
        }
    }
}
