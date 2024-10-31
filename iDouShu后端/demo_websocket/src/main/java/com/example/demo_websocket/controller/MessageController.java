package com.example.demo_websocket.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo_websocket.pojo.Message;
import com.example.demo_websocket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息控制器类，处理与消息相关的操作
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 根据 friendId 分页查询消息
     *
     * @param friendId 朋友 ID
     * @param pageNum 当前页码，默认为 1
     * @param pageSize 每页大小，默认为 30
     * @return 分页结果
     */
    @GetMapping("/list/{friendId}")
    public IPage<Message> getMessagesByFriendId(
            @PathVariable Integer friendId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "30") int pageSize) {
        return messageService.getMessagesByFriendId(friendId, pageNum, pageSize);
    }

    /**
     * 添加新消息
     *
     * @param message 消息对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public boolean createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    /**
     * 删除指定消息
     *
     * @param messageId 消息 ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{messageId}")
    public boolean deleteMessage(@PathVariable Integer messageId) {
        return messageService.deleteMessage(messageId);
    }
}
