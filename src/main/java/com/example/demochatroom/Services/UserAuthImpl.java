package com.example.demochatroom.Services;

import com.example.demochatroom.mongoEntities.User;
import com.example.demochatroom.mongoEntities.UserStatus;
import com.example.demochatroom.mongorepository.UserRepository;
import com.example.demochatroom.mysqlEntities.UserInAuth;
import com.example.demochatroom.mysqlrepository.UserInAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserAuthImpl implements UserAuthService{

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserInAuthRepository userInAuthRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(UserInAuth user) throws Exception{
        System.out.println("I am called");
        try{
            User userInDb=new User();
            userInDb.setUsername(user.getUsername());
            userInDb.setUserStatus(UserStatus.ONLINE);
            userInDb.setFriends(new ArrayList<String>());
            userService.save(userInDb);

            String hashedPassword=bCryptPasswordEncoder.encode(user.getPassword());

            user.setPassword(hashedPassword);
            userInAuthRepository.save(user);
        }
        catch (Exception e){
            System.out.println("woops");
            throw new Exception("user could not be signed up");
        }
    }
}
