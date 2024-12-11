package com.example.demochatroom.Services;

import com.example.demochatroom.Entities.User;
import com.example.demochatroom.Entities.UserStatus;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void save(User user);
    public User findUser(String username);

    public UserStatus findStatus(String username);

    public void addFriend(String username,String friendUsername) throws Exception;
}
