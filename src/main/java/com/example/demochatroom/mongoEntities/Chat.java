package com.example.demochatroom.mongoEntities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document("Chat")
public class Chat {
    @Id
    private String id;

    private String message;

    private String sender;
    private String receiver;
    private LocalDateTime messageDate;
    private ChatMessageStatus chatMessageStatus;
}
