package com.example.demo_websocket.mapper;

import com.example.demo_websocket.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
        @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
        User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
