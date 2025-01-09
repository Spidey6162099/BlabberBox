package com.example.demochatroom.mongorepository;

import com.example.demochatroom.mongoEntities.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat,String> {

    public List<Chat> findAllByReceiverAndSender(String receiver,String sender);
}
