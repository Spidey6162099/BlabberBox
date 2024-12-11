package com.example.demochatroom.Services;

import com.example.demochatroom.Entities.Chat;
import com.example.demochatroom.Entities.ChatRoom;
import com.example.demochatroom.messages.PrivateMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatRoomService {
    public void save(Chat chat);
    public List<Chat> fetchChats(String id);
//    public ChatRoom getTheOne(String id);
}
