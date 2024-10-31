package com.example.demo_websocket;

import com.example.demo_websocket.pojo.Message;
import com.example.demo_websocket.service.FriendService;
import com.example.demo_websocket.service.MessageService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/websocket")
@Component
public class WebsocketServer {

    private static FriendService friendService;
    private static MessageService messageService;

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    public void setMessageService(MessageService messageService) {
        WebsocketServer.messageService = messageService;
    }

    @Autowired
    public void setFriendService(FriendService friendService) {
        WebsocketServer.friendService = friendService;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connection opened: " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Received message: " + message);
        JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
        String action = jsonMessage.get("action").getAsString();

        switch (action) {
            case "send":
                handleSendMessage(jsonMessage);
                break;
            case "join":
                handleJoin(session, jsonMessage);
                break;
            default:
                System.out.println("Unknown action: " + action);
        }
    }

    private void handleSendMessage(JsonObject jsonMessage) throws IOException {
        if (jsonMessage.has("userid") && jsonMessage.has("touserid")) {
            String fromUserId = jsonMessage.get("userid").getAsString();
            String toUserId = jsonMessage.get("touserid").getAsString();
            String content = jsonMessage.get("content").getAsString();
            String name = jsonMessage.get("name").getAsString();
            int friendId = jsonMessage.get("friendid").getAsInt();

            // 创建 Message 对象
            Message msg = new Message();
            msg.setUserId(Integer.parseInt(fromUserId));
            msg.setFriendId(friendId);
            msg.setContent(content);

            // 更新消息状态并保存到数据库
            friendService.updateMessage(friendId, content);
            saveMessageToDatabase(msg);

            // 发送消息给目标用户
            sendMessageToUser(toUserId, fromUserId, content, name);
        } else {
            System.out.println("Missing userid or touserid in the message.");
        }
    }

    private void handleJoin(Session session, JsonObject jsonMessage) throws IOException {
        String username = jsonMessage.get("username").getAsString();
        session.getUserProperties().put("username", username);
        System.out.println(username + " has joined the chat.");
        broadcastMessage("System", username + " has joined the chat.");
    }

    private void sendMessageToUser(String toUserId, String fromUserId, String content, String name) throws IOException {
        for (Session s : sessions) {
            String toId = (String) s.getUserProperties().get("username");
            if (toId != null && toId.equals(toUserId)) {
                s.getBasicRemote().sendText("{\"from\":\"" + fromUserId + "\", \"message\":\"" + content + "\", \"name\":\"" + name + "\"}");
            }
        }
    }

    private void saveMessageToDatabase(Message message) {
        if (messageService.createMessage(message)) {
            System.out.println("Message added successfully");
        } else {
            System.out.println("Failed to add message");
        }
    }

    private void broadcastMessage(String from, String message) throws IOException {
        for (Session s : sessions) {
            s.getBasicRemote().sendText("{\"from\":\"" + from + "\", \"message\":\"" + message + "\"}");
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Connection closed: " + session.getId() + " ID: " + session.getUserProperties().get("username"));
        sessions.remove(session);
    }
}
