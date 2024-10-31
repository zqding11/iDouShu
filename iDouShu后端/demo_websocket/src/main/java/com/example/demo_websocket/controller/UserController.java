package com.example.demo_websocket.controller;

import com.example.demo_websocket.pojo.User;
import com.example.demo_websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dzk
 * @since 2024-10-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param user 用户信息
     * @return 注册成功后的用户信息()
     */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 用户登录接口
     *
     * @param credentials 包含用户名和密码的键值对
     * @return 登录成功的用户信息
     */
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        return userService.login(username, password);
    }

    /**
     * 更新用户信息接口
     *
     * @param user 更新后的用户信息
     * @return 更新操作是否成功
     */
    @PutMapping("/update")
    public boolean updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }

    /**
     * 根据用户ID查询用户信息接口
     *
     * @param id 用户ID
     * @return 查询到的用户信息
     */
    @GetMapping("/search")
    public User getUserById(@RequestParam("id") Integer id) {
        return userService.getUserById(id);
    }
}