package com.example.demochatroom.Controllers;

import com.example.demochatroom.mongoEntities.Chat;
import com.example.demochatroom.Services.ChatRoomServiceImpl;
import com.example.demochatroom.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GeneralController {

    @Autowired
    private ChatRoomServiceImpl chatRoomService;
    @GetMapping("/")
    public String getHomePage(){
        return "home.html";
    }
    @GetMapping("/group")
    public String getGroupMessaging(){
        return "groupChat.html";
    }

    @MessageMapping("/send")
    @SendTo("/topics/he7llo")
    public Message sendMessage(@Payload Message message){

        return message;
    }

    @GetMapping("/api/chats/{id}")
    public ResponseEntity<List<Chat>> getChats(@PathVariable String id){
        List<Chat> chats= chatRoomService.fetchChats(id);
        if(chats!=null){
            return new ResponseEntity<>(chats, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
