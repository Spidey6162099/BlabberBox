package com.example.demochatroom.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Document("ChatRoom")
public class ChatRoom {
    //will be combination of both sender and receiver
    @Id
    private String id;

    private List<Chat> chats;

}
