package com.example.demo_websocket.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo_websocket.pojo.Message;
import com.example.demo_websocket.mapper.MessageMapper;
import com.example.demo_websocket.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public IPage<Message> getMessagesByFriendId(Integer friendId, int pageNum, int pageSize) {
        Page<Message> page = new Page<>(pageNum, pageSize);
        return messageMapper.selectMessagesByFriendId(page, friendId); // 分页查询消息
    }

    @Override
    public boolean createMessage(Message message) {
        return messageMapper.insert(message) > 0; // 返回插入结果
    }

    @Override
    public boolean deleteMessage(Integer messageId) {
        return messageMapper.deleteById(messageId) > 0; // 返回删除结果
    }
}
