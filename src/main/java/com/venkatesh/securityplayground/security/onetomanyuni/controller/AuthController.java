package com.venkatesh.securityplayground.security.onetomanyuni.controller;

import com.venkatesh.securityplayground.security.onetomanyuni.jwt.JwtUtils02;
import com.venkatesh.securityplayground.security.onetomanyuni.model.UserModel;
import com.venkatesh.securityplayground.security.onetomanyuni.repo.UserRepository;
import com.venkatesh.securityplayground.security.onetomanyuni.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils02 jwtUtils02;

    @PostMapping("/signin")
    public UserModel signin(@RequestBody UserModel userModel){
        return userService.addUser(userModel);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserModel userModel){

        //
                Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword())
        );

        if(authentication.isAuthenticated()){
//            System.out.println(authentication.getName() +  " user has been logged in successfully");
//            return authentication.getName() + " user has been logged in successfully";
            String jwtToken = jwtUtils02.generateToken(userModel);
            String result = "authentication.getName() = " + authentication.getName() + "\n" +
            "userModel.getUsername() = " + userModel.getUsername() + "\n" +
            "JWT Token : " + jwtToken;
            return result;
        }
        return "failed login";
    }

    @GetMapping("/hello1") // USER, ADMIN
    public String hello1(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return "This endpoint accessed by " + username + "\n"
                + "his roles \n" + roles;
    }

    @GetMapping("/hello2") // ADMIN
    public String hello2(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return "This endpoint accessed by " + username + "\n"
                + "his roles \n" + roles;
    }

    @GetMapping("/hello3") // USER
    public String hello3(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        return "This endpoint accessed by " + username + "\n"
                + "his roles \n" + roles;
    }

//    public String one(){
//        return "Hello World";
//    }
}
