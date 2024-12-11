package com.example.demochatroom.Repositories;

import com.example.demochatroom.Entities.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat,String> {

    public List<Chat> findAllByReceiverAndSender(String receiver,String sender);
}
