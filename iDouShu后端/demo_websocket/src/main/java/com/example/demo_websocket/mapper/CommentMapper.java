package com.example.demo_websocket.mapper;

import com.example.demo_websocket.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
