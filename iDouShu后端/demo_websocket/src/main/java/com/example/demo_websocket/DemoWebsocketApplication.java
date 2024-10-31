package com.example.demo_websocket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.demo_websocket.mapper")
public class DemoWebsocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoWebsocketApplication.class, args);
    }
}
