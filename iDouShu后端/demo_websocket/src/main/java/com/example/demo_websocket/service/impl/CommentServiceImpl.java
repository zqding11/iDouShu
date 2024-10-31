package com.example.demo_websocket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo_websocket.pojo.Comment;
import com.example.demo_websocket.mapper.CommentMapper;
import com.example.demo_websocket.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public boolean remove(Integer id) {
        return commentMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateById(Comment comment) {
        return commentMapper.updateById(comment) > 0;
    }

    @Override
    public List<Comment> getAllComments(int page, int size, String nid) {
        Page<Comment> commentPage = new Page<>(page, size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        // 添加 nid 的查询条件
        queryWrapper.eq("nid", nid);
        queryWrapper.isNull("parent_id"); // 查询 parent_id 为空的评论

        return commentMapper.selectPage(commentPage, queryWrapper).getRecords();
    }

    @Override
    public List<Comment> getChildComments(Integer parentCommentId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_comment_id", parentCommentId);
        return commentMapper.selectList(queryWrapper);
    }
}
