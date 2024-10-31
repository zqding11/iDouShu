package com.example.demo_websocket.controller;

import com.example.demo_websocket.common.Uploadimage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageUploadController {

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("收到文件上传请求");
        if (file.isEmpty()) {
            return "上传失败，文件为空";
        }

        return Uploadimage.uploadImage(file);
    }
}
