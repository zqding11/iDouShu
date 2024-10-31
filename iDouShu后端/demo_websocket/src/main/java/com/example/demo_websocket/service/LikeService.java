package com.example.demo_websocket.service;

import com.example.demo_websocket.pojo.Like;
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
public interface LikeService extends IService<Like> {

    boolean addLike(Integer uId, Integer nId);
    boolean deleteLike(Integer uId, Integer nId);
    List<Like> getLikesByUId(Integer uId);
}
