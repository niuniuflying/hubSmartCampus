package com.nac.chatroom.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.nac.chatroom.pojo.ResultMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用javax的ServerEndpoint注解实现
 */
@ServerEndpoint("/chat/{username}")
@Component
public class ChatEndPoint {
    //所有客户端
    private static Map<String,ChatEndPoint> clients = new ConcurrentHashMap<>();
    //在线总人数
    private static Integer onlineCount =0;
    //Session对象，用于推送数据
    private Session session;
    //用户名
    private String username;

    //连接建立时执行
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("username")String username){
        System.out.println("连接成功");
        //建立连接时初始化部分数据，并给所有客户端推送消息，增加在线人数
        this.session = session;
        this.username = username;
        ChatEndPoint.onlineCount++;
        clients.put(username,this);
        ResultMessage res = new ResultMessage();
        res.setFromUsername(username);
        res.setOnlineCount(ChatEndPoint.onlineCount);
        res.setOnlineUsernames(clients.keySet());
        sendMessage(res);
    }

    //连接关闭时执行
    @OnClose
    public void onClose(Session session){
        System.out.println("连接关闭");
        //关闭时，减少在线人数，并且移除在线用户名
        ChatEndPoint.onlineCount--;
        clients.remove(username);
        ResultMessage res = new ResultMessage();
        res.setFromUsername(username);
        res.setOnlineCount(ChatEndPoint.onlineCount);
        res.setOnlineUsernames(clients.keySet());
        sendMessage(res);
    }

    /**
     * 推送消息方法，
     * @param resultMessage
     */
    public void sendMessage(ResultMessage resultMessage){
        try{
            resultMessage.setOnlineUsernames(clients.keySet());
            resultMessage.setOnlineCount(onlineCount);
            for (ChatEndPoint item : clients.values()) {
                if (StringUtils.hasLength(resultMessage.getToUsername())&&item.username.equals(resultMessage.getToUsername())){
                    item.session.getAsyncRemote().sendText(new ObjectMapper().writeValueAsString(resultMessage));
                    return;
                }
                //如果没有指定推送客户端或为大厅消息则全部发送
                if (!StringUtils.hasLength(resultMessage.getToUsername()) || resultMessage.getToUsername().equals("大厅")){
                    item.session.getAsyncRemote().sendText(new ObjectMapper().writeValueAsString(resultMessage));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //接收到客户端发送的数据时执行
    @OnMessage
    public void onMessage(String message,Session session){
        ObjectMapper objectMapper = new ObjectMapper();
        ResultMessage resultMessage = null;
        try {
            //将接受到的Json字符串转换为对应的实体类
            resultMessage = objectMapper.readValue(message, ResultMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //推送消息
        sendMessage(resultMessage);
    }
}
