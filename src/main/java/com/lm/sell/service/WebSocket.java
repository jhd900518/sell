package com.lm.sell.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void opOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("WebSocket消息" + "有新的连接" + webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        System.out.println("连接断开" + webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("收到客户端发来de消息");
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSocketSet) {
            System.out.println("websocket广播消息" + message);
            try{
                webSocket.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
