package com.example.demo_websocket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo_websocket.mapper.CollectionMapper;
import com.example.demo_websocket.pojo.Collection;
import com.example.demo_websocket.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    public boolean addCollection(Integer uId, Integer nId) {
        Collection collection = new Collection();
        collection.setUId(uId).setNId(nId);
        return collectionMapper.insert(collection) > 0;
    }

    @Override
    public boolean deleteCollection(Integer uId, Integer nId) {
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uId", uId).eq("nId", nId);
        return collectionMapper.delete(queryWrapper) > 0;
    }

    @Override
    public List<Collection> getCollectionsByUId(Integer uId) {
        QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uId", uId);
        return collectionMapper.selectList(queryWrapper);
    }
}
