package com.example.demochatroom.Controllers;

import com.example.demochatroom.messages.Message;
import com.example.demochatroom.messages.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {


    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public  MessageController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate=messagingTemplate;
    }




    @MessageMapping("/addUser")
    @SendTo("/topics/hello")
    public Message addUser(Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor){

        simpMessageHeaderAccessor.getSessionAttributes().put("user",message.getSender());
        simpMessageHeaderAccessor.getSessionAttributes().put("color",message.getColor());
        return new Message(message.getSender(),"joins the fray",message.getColor());
    }
    @MessageMapping("/private")
    public void sendSpecific(PrivateMessage privateMessage){
        messagingTemplate.convertAndSendToUser(privateMessage.getReceiver(),"/queue/message",privateMessage.getMessage());
    }
}
