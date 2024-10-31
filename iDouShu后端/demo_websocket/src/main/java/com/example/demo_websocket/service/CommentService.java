package com.example.demo_websocket.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo_websocket.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Service
public interface CommentService extends IService<Comment> {

    List<Comment> getAllComments(int page, int size,String nid);

    List<Comment> getChildComments(Integer parentCommentId);

    boolean remove(Integer id);
}
