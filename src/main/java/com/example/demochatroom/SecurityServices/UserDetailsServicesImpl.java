package com.example.demochatroom.SecurityServices;

import com.example.demochatroom.Services.UserServiceImpl;
import com.example.demochatroom.mysqlEntities.UserInAuth;
import com.example.demochatroom.mysqlrepository.UserInAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {

    @Autowired
    private UserInAuthRepository userInAuthRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInAuth userInDb=userInAuthRepository.findByUsername(username);

        if(userInDb==null){
            throw new UsernameNotFoundException("user could not be found");
        }
        return new UserDetailsImpl(userInDb);
    }
}
