package com.example.demochatroom.SecurityServices;

import com.example.demochatroom.mysqlEntities.Role;
import com.example.demochatroom.mysqlEntities.UserInAuth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class UserDetailsImpl implements UserDetails {

    private UserInAuth user;

    Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UserInAuth user){

        this.user=user;
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(Role role :user.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
//        return authorities;
        this.authorities=authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
