package com.example.springbootreg.controller.ws;


import com.example.springbootreg.pojo.Message;
import com.example.springbootreg.utils.OnlineUserUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;


@Controller
public class WebsocketController {

    @Autowired
    private SimpMessagingTemplate template;


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        System.out.println("message received: " + message.getMessage());
        System.out.println("message received: " + message.getSender());
        System.out.println("message received: " + message.getReceiver());
//        Thread.sleep(1000); // simulated delay
        return message;
    }


    @MessageMapping("/sendUser")
    @SendToUser(destinations = "/user/queue/greetings",broadcast = false)
    public void sendToUser(Message message) throws Exception {
//        OnlineUserUtil.getOnlineUsers();
//        System.out.println(OnlineUserUtil.getOnlineUsers());
//        System.out.println("message received: " + message.getIsCallback());
        System.out.println("message message: " + message.getMessage());
        //Message newMessage = new Message(message.getMessage(), message.getReceiver(), message.getSender());
        System.out.println("message sender: " + message.getSender());
        System.out.println("message receiver: " + message.getReceiver());
        String receiverId = message.getReceiver();
        String destination = "/user/" + receiverId + "/queue/greetings";
        template.convertAndSend(destination, message);
    }


//    @MessageMapping("/messageLogin")
//    public void messageLogin(Message message) throws Exception {
//
//    }

}