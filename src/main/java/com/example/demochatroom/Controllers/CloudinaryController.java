package com.example.demochatroom.Controllers;


import com.example.demochatroom.Exceptions.FileIsEmptyException;
import com.example.demochatroom.Services.ChatRoomServiceImpl;
import com.example.demochatroom.Services.ChatServiceImpl;
import com.example.demochatroom.Services.CloudinaryUploader;

import com.example.demochatroom.Services.UserServiceImpl;
import com.example.demochatroom.messages.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Map;
@Controller
public class CloudinaryController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private ChatServiceImpl chatService;

    @Autowired
    private ChatRoomServiceImpl chatRoomServiceImpl;

    @Autowired
    private CloudinaryUploader cloudinaryUploader;

    @PostMapping("/api/files")
    public ResponseEntity<Map<String,String>> processFile(@RequestParam("sender") String sender,@RequestParam("receiver") String receiver
    ,@RequestParam("file") MultipartFile file){
        System.out.println(sender);
        System.out.println(receiver);
        if(file.isEmpty()){
            throw new FileIsEmptyException("file received was empty");
        }
        System.out.println(file.getOriginalFilename());


        try {
            File newFile=new File(System.getProperty("java.io.tmpdir") + "/"+file.getOriginalFilename());
            file.transferTo(newFile);
            Map response=cloudinaryUploader.upload(newFile);
//            System.out.println(newFile.getName());
            String url="link|"+(String) response.get("secure_url");
//            System.out.println(url);

            //send to the guy this is intened for , i.e the link

            PrivateMessage privateMessage=new PrivateMessage();
            privateMessage.setMessage(url);
            privateMessage.setReceiver(receiver);
            privateMessage.setSender(sender);

            //save the link message as simple chat message

            chatService.save(privateMessage);
            String destUrl="/queue/reciever/"+receiver;
            simpMessagingTemplate.convertAndSend(destUrl,privateMessage);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message",url));
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","something went wrong"));
        }

    }
}
