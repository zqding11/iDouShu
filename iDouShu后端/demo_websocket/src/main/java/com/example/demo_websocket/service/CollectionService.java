package com.example.demo_websocket.service;

import com.example.demo_websocket.pojo.Collection;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Service
public interface CollectionService extends IService<Collection> {

    boolean addCollection(Integer uId, Integer nId);
    boolean deleteCollection(Integer uId, Integer nId);
    List<Collection> getCollectionsByUId(Integer uId);
}
