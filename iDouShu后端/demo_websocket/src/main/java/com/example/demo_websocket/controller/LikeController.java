package com.example.demo_websocket.controller;

import com.example.demo_websocket.pojo.Like;
import com.example.demo_websocket.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 点赞控制器类，处理与点赞相关的操作
 */
@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 添加点赞
     *
     * @param uId 用户 ID
     * @param nId 笔记 ID
     * @return 操作结果
     */
    @PostMapping("/add")
    public String addLike(@RequestParam Integer uId, @RequestParam Integer nId) {
        if (likeService.addLike(uId, nId)) {
            return "Like added successfully";
        } else {
            return "Failed to add like";
        }
    }

    /**
     * 删除点赞
     *
     * @param uId 用户 ID
     * @param nId 笔记 ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    public String deleteLike(@RequestParam Integer uId, @RequestParam Integer nId) {
        if (likeService.deleteLike(uId, nId)) {
            return "Like deleted successfully";
        } else {
            return "Failed to delete like";
        }
    }

    /**
     * 根据用户 ID 获取用户的所有点赞记录
     *
     * @param uId 用户 ID
     * @return 点赞列表
     */
    @GetMapping("/user/{uId}")
    public List<Like> getLikesByUId(@PathVariable Integer uId) {
        return likeService.getLikesByUId(uId);
    }
}
