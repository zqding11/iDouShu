package com.example.demo_websocket.service;

import com.example.demo_websocket.pojo.Friend;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dzk
 * @since 2024-10-28
 */
@Service
public interface FriendService extends IService<Friend> {

    boolean addFriend(Integer user1Id, Integer user2Id);


    boolean markAsRead(Integer user1Id, Integer user2Id);

    List<Friend> getFriends(Integer userId);

    boolean updateMessage(Integer fid, String message);

    Integer findFriendId(Integer user1Id, Integer user2Id);
}
