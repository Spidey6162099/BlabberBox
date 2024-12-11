package com.example.demochatroom.Services;

import com.example.demochatroom.Entities.User;
import com.example.demochatroom.Entities.UserStatus;
import com.example.demochatroom.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public void save(User user) {
        try {
            //first check if it already exists
            User userInDatabase=userRepository.findByUsername(user.getUsername());
            if(userInDatabase==null){
                userRepository.save(user);
            }

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserStatus findStatus(String username) {

        User user=userRepository.findByUsername(username);
        if(user!=null){
            return user.getUserStatus();
        }
        else{
            return UserStatus.NOTEXISTS;
        }
    }

    @Override
    @Transactional
    public void addFriend(String username, String friendUsername) throws Exception {


        //find the user
        User user=userRepository.findByUsername(username);

        //now try fiding the friend in the database of chatapp


        User friend = userRepository.findByUsername(friendUsername);



        if(friend!=null) {
            List<String> friends = user.getFriends();
            int flag=0;
            for(var i:friends){
                if(i.equals(friendUsername)){
                    flag=1;
                    break;
                }
            }
            if(flag==0) {

                friends.add(friendUsername);
                friend.getFriends().add(username);

                userRepository.save(user);
                userRepository.save(friend);

            }

        }
        else{
            throw new Exception("friend not present on app");
        }
    }
}
