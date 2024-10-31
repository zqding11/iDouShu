package com.example.demo_websocket.controller;

import com.example.demo_websocket.pojo.Collection;
import com.example.demo_websocket.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏控制器类，处理与用户收藏相关的操作
 *
 * @author dzk
 * @since 2024-10-27
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 添加收藏
     *
     * @param uId 用户 ID
     * @param nId 笔记 ID
     * @return 操作结果
     */
    @PostMapping("/add")
    public String addCollection(@RequestParam Integer uId, @RequestParam Integer nId) {
        if (collectionService.addCollection(uId, nId)) {
            return "收藏成功";
        } else {
            return "收藏失败";
        }
    }

    /**
     * 删除收藏
     *
     * @param uId 用户 ID
     * @param nId 笔记 ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    public String deleteCollection(@RequestParam Integer uId, @RequestParam Integer nId) {
        if (collectionService.deleteCollection(uId, nId)) {
            return "取消收藏成功";
        } else {
            return "取消收藏失败";
        }
    }

    /**
     * 根据用户 ID 获取收藏列表
     *
     * @param uId 用户 ID
     * @return 收藏列表
     */
    @GetMapping("/user/{uId}")
    public List<Collection> getCollectionsByUId(@PathVariable Integer uId) {
        return collectionService.getCollectionsByUId(uId); // 返回用户收藏的列表
    }
}
