package com.example.demochatroom.mongorepository;

import com.example.demochatroom.mongoEntities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends MongoRepository<User,String> {
    User findByUsername(String username);
}
