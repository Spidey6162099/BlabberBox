package com.example.demochatroom.Services;

import com.example.demochatroom.mongoEntities.Chat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatRoomService {
    public void save(Chat chat);
    public List<Chat> fetchChats(String id);
//    public ChatRoom getTheOne(String id);
}
