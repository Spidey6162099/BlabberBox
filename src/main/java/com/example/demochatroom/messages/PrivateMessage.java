package com.example.demochatroom.messages;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivateMessage {
    private String sender;
    private String receiver;
    private String message;

}
