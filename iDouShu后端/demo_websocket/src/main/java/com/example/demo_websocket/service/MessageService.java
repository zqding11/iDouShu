package com.example.demo_websocket.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo_websocket.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dzk
 * @since 2024-10-28
 */
@Service
public interface MessageService extends IService<Message> {
    IPage<Message> getMessagesByFriendId(Integer friendId, int pageNum, int pageSize);
    boolean createMessage(Message message);
    boolean deleteMessage(Integer messageId);
}
