package com.example.demo_websocket.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo_websocket.pojo.Comment;
import com.example.demo_websocket.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 评论控制器类，处理与评论相关的操作
 *
 * @author dzk
 * @since 2024-10-27
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     *
     * @param comment 评论对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public boolean addComment(@RequestBody Comment comment) {
        comment.setCreateTime(new Date()); // 填充创建时间
        return commentService.save(comment); // 返回保存结果
    }

    /**
     * 删除评论
     *
     * @param id 评论 ID
     */
    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.remove(id); // 删除指定评论
    }

    /**
     * 更新评论
     *
     * @param comment 评论对象，包含更新后的数据
     * @return 操作结果
     */
    @PutMapping("/update")
    public boolean updateComment(@RequestBody Comment comment) {
        return commentService.updateById(comment); // 返回更新结果
    }

    /**
     * 根据 ID 获取评论
     *
     * @param id 评论 ID
     * @return 评论对象
     */
    @GetMapping("/get/{id}")
    public Comment getCommentById(@PathVariable Integer id) {
        return commentService.getById(id); // 返回指定评论
    }

    /**
     * 获取所有评论
     *
     * @param nid 笔记 ID
     * @param page 当前页码
     * @param size 每页大小
     * @return 评论列表
     */
    @GetMapping("/list")
    public List<Comment> getAllComments(
            @RequestParam String nid,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return commentService.getAllComments(page, size, nid); // 返回评论列表
    }

    /**
     * 根据父评论 ID 获取子评论
     *
     * @param parentCommentId 父评论 ID
     * @return 子评论列表
     */
    @GetMapping("/children/{parentCommentId}")
    public List<Comment> getChildComments(@PathVariable Integer parentCommentId) {
        return commentService.getChildComments(parentCommentId); // 返回子评论列表
    }
}
