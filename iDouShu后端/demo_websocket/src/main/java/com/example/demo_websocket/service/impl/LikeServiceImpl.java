package com.example.demo_websocket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo_websocket.pojo.Like;
import com.example.demo_websocket.mapper.LikeMapper;
import com.example.demo_websocket.service.LikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Override
    public boolean addLike(Integer uId, Integer nId) {
        Like like = new Like();
        like.setUId(uId).setNId(nId); // 设置用户ID和笔记ID
        return likeMapper.insert(like) > 0; // 返回插入结果
    }

    @Override
    public boolean deleteLike(Integer uId, Integer nId) {
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uId", uId).eq("nId", nId); // 创建查询条件
        return likeMapper.delete(queryWrapper) > 0; // 返回删除结果
    }

    @Override
    public List<Like> getLikesByUId(Integer uId) {
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uId", uId); // 根据用户ID查询
        return likeMapper.selectList(queryWrapper); // 返回点赞列表
    }
}
