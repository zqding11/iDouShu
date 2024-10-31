package com.example.demo_websocket.service;

import com.example.demo_websocket.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Service
public interface UserService {
    User register(User user);
    User login(String username, String password);
    boolean updateUserInfo(User user);
    User getUserById(Integer id);
}
