package com.example.demo_websocket.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo_websocket.pojo.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dzk
 * @since 2024-10-28
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    @Select("SELECT * FROM message WHERE friend_id = #{friendId} ORDER BY createtime DESC")
    IPage<Message> selectMessagesByFriendId(Page<?> page, @Param("friendId") Integer friendId);
    @Insert("INSERT INTO message (user_id, content, friend_id) VALUES (#{userId}, #{content}, #{friendId})")
    int insert(Message message);
}

