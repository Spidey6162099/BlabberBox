package com.example.demochatroom.Controllers;

import com.example.demochatroom.DTOs.FriendMessage;
import com.example.demochatroom.Entities.Chat;
import com.example.demochatroom.Entities.ChatMessageStatus;
import com.example.demochatroom.Entities.User;
import com.example.demochatroom.Entities.UserStatus;
import com.example.demochatroom.Services.*;
import com.example.demochatroom.messages.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PrivateMessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ChatServiceImpl chatService;

    @Autowired
    private ChatRoomServiceImpl chatRoomServiceImpl;

    @MessageMapping("/addPrivateUser")
    public void addPrivateUser(@Payload String username){

        User newUser=new User();
        newUser.setUsername(username);
        newUser.setUserStatus(UserStatus.ONLINE);
        newUser.setFriends(new ArrayList<String>());
        userService.save(newUser);
    }

    //now this sends messages so this will be used for logging chats into database
    @MessageMapping("/sendPrivate")
    public void sendPrivateMessage(@Payload PrivateMessage privateMessage){


        //now we can send to the specific user using the simpMessaging like so
        //assume destination to be example/reciever

        //this saves the chat in db
        chatService.save(privateMessage);

        //now to save the chatRoom

        //now in similar vein
        String destination="/queue/reciever/"+privateMessage.getReceiver();


        simpMessagingTemplate.convertAndSend(destination,privateMessage);
    }

    @GetMapping ("/privateMessage")
    public String getPrivateMessage(){
        return "privateChat.html";
    }

    @MessageMapping("/addFriends")
    public void addFriend(@Payload FriendMessage friendMessage){
        String friendUsername=friendMessage.getFriendUsername();
        String user=friendMessage.getUserUsername();
        System.out.println("user: "+user+"friend "+friendUsername);
        //remove ""
        try{

            userService.addFriend(user,friendUsername);
            String destination="/queue/friends/"+user;
            simpMessagingTemplate.convertAndSend(destination,friendUsername);
        }
        catch (Exception e){
            System.out.println("illa");
        }
    }

    @GetMapping("/friends/{user}")
    public ResponseEntity<List<String>> getFriends(@PathVariable String user){

        User userInDb=userService.findUser(user);
        if(userInDb==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(userInDb.getFriends(),HttpStatus.OK);
        }
    }

}
