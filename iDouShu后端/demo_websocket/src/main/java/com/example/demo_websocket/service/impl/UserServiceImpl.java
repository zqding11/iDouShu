package com.example.demo_websocket.service.impl;

import com.example.demo_websocket.pojo.User;
import com.example.demo_websocket.mapper.UserMapper;
import com.example.demo_websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(User user) {
        if (userMapper.insert(user) > 0) {
            return user; // 注册成功，返回用户信息
        }
        return null; // 注册失败
    }

    @Override
    public User login(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password); // 返回用户信息
    }

    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateById(user) > 0; // 返回更新结果
    }

    @Override
    public User getUserById(Integer id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setPassword(null);  // 隐藏密码字段
        }
        return user; // 返回用户信息
    }
}
