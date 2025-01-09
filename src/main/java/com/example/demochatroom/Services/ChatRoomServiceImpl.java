package com.example.demochatroom.Services;

import com.example.demochatroom.mongoEntities.Chat;
import com.example.demochatroom.mongoEntities.ChatRoom;
import com.example.demochatroom.mongorepository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Override
    public void save(Chat chat) {
        ChatRoom chatRoomInDb;

        //if exists
        if(chatRoomRepository.find(chat.getReceiver()+','+chat.getSender())!=null){
            chatRoomInDb=chatRoomRepository.find(chat.getReceiver()+','+chat.getSender());
            List<Chat> newChats=chatRoomInDb.getChats();
            newChats.add(chat);
            chatRoomInDb.setChats(newChats);
            chatRoomRepository.save(chatRoomInDb);
        }

        else if(chatRoomRepository.find(chat.getSender()+','+chat.getReceiver())!=null){
            chatRoomInDb=chatRoomRepository.find(chat.getSender()+','+chat.getReceiver());
            List<Chat> newChats=chatRoomInDb.getChats();
            newChats.add(chat);
            chatRoomInDb.setChats(newChats);
            chatRoomRepository.save(chatRoomInDb);
        }
        //if not then make
        else {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(chat.getSender() + ',' + chat.getReceiver());
            List<Chat> newChat = new ArrayList<Chat>();
            newChat.add(chat);
            chatRoom.setChats(newChat);

            chatRoomRepository.save(chatRoom);
        }
    }

    public List<Chat> fetchChats(String id){
        String chatRoomId=id;
        ChatRoom chatRoom=chatRoomRepository.find(id);
        if(chatRoom!=null){
            return chatRoom.getChats();
        }
       else{
           return null;
        }
    }

}
