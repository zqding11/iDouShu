package com.example.demo_websocket.service.impl;

import com.example.demo_websocket.pojo.Image;
import com.example.demo_websocket.mapper.ImageMapper;
import com.example.demo_websocket.service.ImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dzk
 * @since 2024-10-27
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

}
