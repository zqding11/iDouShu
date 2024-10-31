package com.example.demo_websocket.service.impl;

import com.example.demo_websocket.pojo.Friend;
import com.example.demo_websocket.mapper.FriendMapper;
import com.example.demo_websocket.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    // 添加好友关系
    @Override
    public boolean addFriend(Integer user1Id, Integer user2Id) {
        // 查询是否已存在好友关系
        if (friendMapper.existsByUserIds(user1Id, user2Id)) {
            return false; // 已存在
        }

        Integer totalFriendsCount = friendMapper.count();
        Integer fid = totalFriendsCount / 2 + 1; // 假设fid从1开始且每个用户对称

        // 创建好友关系对象
        Friend friend1 = new Friend()
                .setFid(fid) // 设置fid
                .setUser1Id(user1Id)
                .setUser2Id(user2Id)
                .setMessage("") // 初始消息为空
                .setState(0); // 初始状态未读

        Friend friend2 = new Friend()
                .setFid(fid) // 设置fid（如果需要两个方向保持相同fid）
                .setUser1Id(user2Id)
                .setUser2Id(user1Id)
                .setMessage("")
                .setState(0);

        // 保存记录
        friendMapper.insert(friend1);
        friendMapper.insert(friend2);
        return true;
    }

    // 更新消息
    @Override
    public boolean updateMessage(Integer fid, String message) {
        // 查询 fid 匹配的两条记录（user1 和 user2 的互相关系）
        List<Friend> friends = friendMapper.findByFid(fid);

        // 如果查询到的记录数不为2，返回更新失败
        if (friends.size() != 2) {
            return false;
        }

        // 更新两条记录的 message 和 state
        for (Friend friend : friends) {
            friend.setMessage(message);
            friend.setState(0); // 设置为未读
            friendMapper.updateFriend(friend);
        }
        return true;
    }

    // 标记为已读
    @Override
    public boolean markAsRead(Integer user1Id, Integer user2Id) {
        return friendMapper.updateStateToRead(user1Id, user2Id) > 0; // 返回更新结果
    }

    // 查询好友列表
    @Override
    public List<Friend> getFriends(Integer userId) {
        return friendMapper.findByUserId(userId); // 返回好友列表
    }

    @Override
    public Integer findFriendId(Integer user1Id, Integer user2Id) {
        return friendMapper.findFriendId(user1Id, user2Id); // 查找好友ID
    }
}
