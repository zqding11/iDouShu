package com.example.demo_websocket.controller;

import com.example.demo_websocket.pojo.Image;
import com.example.demo_websocket.service.ImageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图片控制器类，处理与图片相关的操作
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 添加图片
     *
     * @param nId 笔记 ID
     * @param image 图片对象
     * @return 操作结果
     */
    @PostMapping("/add/{nId}")
    public boolean addImage(@PathVariable String nId, @RequestBody Image image) {
        image.setNId(nId); // 设置图片对应的笔记 ID
        return imageService.save(image); // 返回保存结果
    }

    /**
     * 根据笔记 ID 获取所有图片
     *
     * @param nId 笔记 ID
     * @return 图片列表
     */
    @GetMapping("/list/{nId}")
    public List<Image> getImagesByNid(@PathVariable String nId) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("n_id", nId); // 查询条件
        return imageService.list(queryWrapper);
    }

    /**
     * 更新指定笔记 ID 下的所有图片
     *
     * @param nId 笔记 ID
     * @param images 图片列表
     * @return 操作结果
     */
    @PutMapping("/update/{nId}")
    public boolean updateImages(@PathVariable String nId, @RequestBody List<Image> images) {
        // 删除指定笔记 ID 下的所有图片
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("n_id", nId);
        imageService.remove(queryWrapper);

        // 添加新的图片
        for (Image image : images) {
            image.setNId(nId); // 设置图片的笔记 ID
            imageService.save(image);
        }

        return true; // 返回更新结果
    }

    /**
     * 删除指定笔记 ID 下的所有图片
     *
     * @param nId 笔记 ID
     * @return 操作结果
     */
    @DeleteMapping("/delete/{nId}")
    public boolean deleteImagesByNId(@PathVariable String nId) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("n_id", nId);
        return imageService.remove(queryWrapper); // 返回删除结果
    }
}
