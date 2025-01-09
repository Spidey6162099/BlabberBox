package com.example.demochatroom.Services;

import com.example.demochatroom.mysqlEntities.UserInAuth;

public interface UserAuthService {
    public void saveUser(UserInAuth user) throws Exception;
}
