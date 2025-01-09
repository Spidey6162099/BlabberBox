package com.example.demochatroom.Services;

import com.example.demochatroom.mongoEntities.Chat;
import com.example.demochatroom.mongoEntities.ChatMessageStatus;
import com.example.demochatroom.mongoEntities.UserStatus;
import com.example.demochatroom.mongorepository.ChatRepository;
import com.example.demochatroom.messages.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatRoomServiceImpl chatRoomService;

    @Autowired
    private UserService userService;
    @Override
    public void save(PrivateMessage privateMessage) {

        Chat chat=new Chat();
        chat.setMessage(privateMessage.getMessage());
        chat.setSender(privateMessage.getSender());
        chat.setReceiver(privateMessage.getReceiver());
        chat.setMessageDate(LocalDateTime.now());

        //now one needs to check the status of user whether they are online or offline
        UserStatus userStatus=userService.findStatus(privateMessage.getReceiver());
        if(userStatus!=UserStatus.NOTEXISTS){
            if(userStatus==UserStatus.ONLINE){
                chat.setChatMessageStatus(ChatMessageStatus.READ);
            }
            else{
                chat.setChatMessageStatus(ChatMessageStatus.UNREAD);
            }
        }

        chatRepository.save(chat);
        chatRoomService.save(chat);

    }

    @Override
    public List<Chat> getAllBySenderAndReceiver(String sender, String receiver) {
        return chatRepository.findAllByReceiverAndSender(receiver,sender);
    }
}
