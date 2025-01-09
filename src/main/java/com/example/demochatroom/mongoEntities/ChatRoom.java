package com.example.demochatroom.mongoEntities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Document("ChatRoom")
@Data
public class ChatRoom {
    //will be combination of both sender and receiver
    @Id
    private String id;

    private List<Chat> chats;

}
