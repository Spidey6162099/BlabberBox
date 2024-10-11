package com.example.demochatroom.Controllers;

import com.example.demochatroom.Message;
import com.sun.tools.jconsole.JConsoleContext;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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


    @MessageMapping("/send")
    @SendTo("/topics/hello")
    public Message sendMessage(@Payload Message message){

       return message;
    }

    @MessageMapping("/addUser")
    @SendTo("/topics/hello")
    public Message addUser(Message message, SimpMessageHeaderAccessor simpMessageHeaderAccessor){

        simpMessageHeaderAccessor.getSessionAttributes().put("user",message.getSender());
        simpMessageHeaderAccessor.getSessionAttributes().put("color",message.getColor());
        return new Message(message.getSender(),"joins the fray",message.getColor());
    }
    @MessageMapping("/private")

    public void sendSpecific(Message message){
//        messagingTemplate.convertAndSendToUser(message.getRecipient(),"/queue/reply",message);
    }
}
