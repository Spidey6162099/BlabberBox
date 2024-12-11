package com.example.demochatroom.Repositories;

import com.example.demochatroom.Entities.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {

    @Query("{'_id': ?0}")
    public ChatRoom find(String id);
}
