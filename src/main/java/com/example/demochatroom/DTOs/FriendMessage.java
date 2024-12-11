package com.example.demochatroom.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.handler.annotation.SendTo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendMessage {
    private String friendUsername;
    private String userUsername;

}
