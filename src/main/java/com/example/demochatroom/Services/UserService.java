package com.example.demochatroom.Services;

import com.example.demochatroom.mongoEntities.User;
import com.example.demochatroom.mongoEntities.UserStatus;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void save(User user) throws Exception;
    public User findUser(String username);

    public UserStatus findStatus(String username);

    public void addFriend(String username,String friendUsername) throws Exception;
}
