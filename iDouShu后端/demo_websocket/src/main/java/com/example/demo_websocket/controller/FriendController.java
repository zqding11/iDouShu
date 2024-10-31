package com.example.demo_websocket.controller;

import com.example.demo_websocket.pojo.Friend;
import com.example.demo_websocket.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 好友控制器类，处理与好友相关的操作
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * 添加好友
     *
     * @param user1Id 用户 1 的 ID
     * @param user2Id 用户 2 的 ID
     * @return 好友 ID
     */
    @GetMapping("/add/{user1Id}/{user2Id}")
    public Integer addFriend(@PathVariable Integer user1Id, @PathVariable Integer user2Id) {
        Integer fid = friendService.findFriendId(user1Id, user2Id);
        if (fid != null && fid > -1) { // 确保 fid 不为 null
            return fid; // 返回已存在的 fid
        } else {
            boolean added = friendService.addFriend(user1Id, user2Id); // 添加好友
            if (added) {
                fid = friendService.findFriendId(user1Id, user2Id); // 重新查找 fid
                return fid; // 返回新创建的 fid
            }
            return null; // 可以考虑抛出异常或返回一个合适的错误码
        }
    }

    /**
     * 更新好友消息
     *
     * @param fid 消息 ID
     * @param message 新消息内容
     * @return 操作结果
     */
    @PutMapping("/update/{fid}/{message}")
    public ResponseEntity<String> updateMessage(@PathVariable Integer fid, @PathVariable String message) {
        boolean updated = friendService.updateMessage(fid, message);
        if (updated) {
            return ResponseEntity.ok("消息已更新");
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }

    /**
     * 标记好友消息为已读
     *
     * @param user1Id 用户 1 的 ID
     * @param user2Id 用户 2 的 ID
     * @return 操作结果
     */
    @PutMapping("/read/{user1Id}/{user2Id}")
    public ResponseEntity<String> markAsRead(@PathVariable Integer user1Id, @PathVariable Integer user2Id) {
        boolean marked = friendService.markAsRead(user1Id, user2Id);
        if (marked) {
            return ResponseEntity.ok("消息已标记为已读");
        } else {
            return ResponseEntity.badRequest().body("标记失败");
        }
    }

    /**
     * 查询好友列表
     *
     * @param userId 用户 ID
     * @return 好友列表
     */
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Friend>> getFriends(@PathVariable Integer userId) {
        List<Friend> friends = friendService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }
}
