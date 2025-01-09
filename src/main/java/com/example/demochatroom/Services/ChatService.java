package com.example.demochatroom.Services;

import com.example.demochatroom.mongoEntities.Chat;
import com.example.demochatroom.messages.PrivateMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {
    public void save(PrivateMessage privateMessage);
    public List<Chat> getAllBySenderAndReceiver(String sender, String receiver);
}
