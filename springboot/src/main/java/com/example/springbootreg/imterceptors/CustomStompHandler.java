package com.example.springbootreg.imterceptors;


import com.example.springbootreg.utils.OnlineUserUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class CustomStompHandler implements ChannelInterceptor {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
//        String login = accessor.getLogin();
//        String destination = accessor.getDestination();
//        System.out.println("1:"+destination);
        String sessionID = message.getHeaders().get("simpSessionId").toString();

//        long[] heartbeat = accessor.getHeartbeat();
//        System.out.println(command);
        String checkCommand = command.toString();
        switch (checkCommand){
//            case "CONNECT":{
//                System.out.println("connect");
////                System.out.println(login);
////                OnlineUserUtil.getOnlineUsers().get(sessionID);
//                System.out.println(OnlineUserUtil.getOnlineUsers());
//                if (login.equals("undefined")){
//                    System.out.println(login);
//                    break;
//                }
//                boolean userFound = false;
//                for (Map.Entry<String, String> entry : OnlineUserUtil.getOnlineUsers().entrySet()) {
////                    System.out.println("Online User Login: " + entry.getValue());
//                    if (entry.getKey().equals(login)){
//                        System.out.println("onLogin");
//                        System.out.println("login:"+login);
//                        System.out.println(sessionID);
//                        OnlineUserUtil.getOnlineUsers().put(login, sessionID);
//                        System.out.println(OnlineUserUtil.getOnlineUsers());
////                        System.out.println(OnlineUserUtil.getOnlineUsers().get(login));
//                        userFound = true;
//                        break;
//                    }
//                }
//                if (!userFound) {
//                    System.out.println("setLogin");
//                    OnlineUserUtil.addUser(login,sessionID);
//                    System.out.println(OnlineUserUtil.getOnlineUsers());
//                }
////                for (Map.Entry<String, String> entry : OnlineUserUtil.getOnlineUsers().entrySet()) {
////                    System.out.println("Online User Session ID: " + entry.getKey());
////                }
//                break;
//            }
            case "SUBSCRIBE":{
                String destination = accessor.getDestination();
                int start = "/user/".length();
                String result = destination.substring(start, destination.length() - ("/queue/greetings".length()));


                boolean userFound = false;
                for (Map.Entry<String, String> entry : OnlineUserUtil.getOnlineUsers().entrySet()) {
                    if (entry.getKey().equals(result)){
                        System.out.println("onLogin");
                        System.out.println("1:"+result);
                        System.out.println(sessionID);
                        OnlineUserUtil.getOnlineUsers().put(result, sessionID);
                        System.out.println(OnlineUserUtil.getOnlineUsers());
                        userFound = true;
                        break;
                    }
                }
                if (!userFound) {
                    System.out.println("setLogin");
                    OnlineUserUtil.addUser(result,sessionID);
                    System.out.println(OnlineUserUtil.getOnlineUsers());
                }


                break;
            }
            case "DISCONNECT":{
                System.out.println("disconnect");

//                Boolean sessionFound = false;
                for (Map.Entry<String, String> entry : OnlineUserUtil.getOnlineUsers().entrySet()) {
                    if (entry.getValue().equals(sessionID)){
                        System.out.println("sessionFound");
                        System.out.println(sessionID);
                        System.out.println(entry.getKey());
//                        System.out.println(sessionID);
                        OnlineUserUtil.removeUser(entry.getKey());
                        System.out.println(OnlineUserUtil.getOnlineUsers());
//                        System.out.println(OnlineUserUtil.getOnlineUsers());
//                        sessionFound = true;
                        break;
                    }
                }

//                OnlineUserUtil.removeUser(sessionID.toString());
                break;
            }
            case "SEND":{
                System.out.println("send");

                Object payload = message.getPayload();


                String payloadString = new String((byte[]) payload, StandardCharsets.UTF_8);

                System.out.println(OnlineUserUtil.getOnlineUsers());
                JSONObject jsonObject = new JSONObject(payloadString);
                String receiver = jsonObject.getString("receiver");
                System.out.println("receiver:"+receiver);
//                String isCallback = jsonObject.getString("isCallback");
                System.out.println(jsonObject);
//                if (jsonObject.getString("receiver").equals())
                for (Map.Entry<String, String> entry : OnlineUserUtil.getOnlineUsers().entrySet()){
                    System.out.println("entry:"+entry.getKey());
                    if (entry.getKey().equals(receiver)){
                        System.out.println("success");
                        break;
                    }else {
                        System.out.println("fail");


                    }


                }
//                System.out.println(accessor);
                System.out.println(OnlineUserUtil.getOnlineUsers());
                return message;

            }
        }


//        System.out.println(heartbeat);
        return message;
    }

}
