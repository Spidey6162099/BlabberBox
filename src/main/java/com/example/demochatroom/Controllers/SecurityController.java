package com.example.demochatroom.Controllers;

import com.example.demochatroom.Services.UserAuthImpl;
import com.example.demochatroom.mysqlEntities.Role;
import com.example.demochatroom.mysqlEntities.UserInAuth;
import com.example.demochatroom.mysqlrepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@Controller
public class SecurityController {

    @Autowired
    private UserAuthImpl authService;

    @Autowired
    private RoleRepository roleRepository;




//    endpoint to call to signup a new user
    @PostMapping("/signup/user")
    public ResponseEntity<String> signupNewUser(@RequestBody UserInAuth user){
        //save the user
        System.out.println("hello");
        System.out.println(user.getUsername());
        try {
            authService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>> getAuthenticatedUser(@AuthenticationPrincipal Object principal){
        String user=null;
        if(principal instanceof OAuth2User){
             user= ((OAuth2User)(principal)).getAttribute("name");
            }
        else if(principal instanceof UserDetails) {
            user=(((UserDetails) principal).getUsername());
        }
        else if(principal instanceof Principal){
            user=((Principal) principal).getName();
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
            return new ResponseEntity<>(Collections.singletonMap("user",user),HttpStatus.OK);

    }

    @PostMapping("/admin/roles")
    public ResponseEntity<String> addNewRole(@RequestBody Role role){
        try{

            roleRepository.save(role);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


    @GetMapping("/login")
    public String showLoginForm(){
        return "login.html";
    }

    @GetMapping("/signup")
    public String showSignUpForm(){return "signup.html";}
}
