package com.venkatesh.securityplayground.security.onetooneuni.controller;

import com.venkatesh.securityplayground.security.onetooneuni.model.UserModel;
import com.venkatesh.securityplayground.security.onetooneuni.repo.UserModelRepo;
import com.venkatesh.securityplayground.security.onetooneuni.security.JwtUtils01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class OnetoOneUniController {

    @Autowired
    private UserModelRepo userModelRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils01 jwtUtils01;

    @PostMapping("/signin")
    public String signin(@RequestBody UserModel userModel){
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userModelRepo.save(userModel).toString();
    }

    @PostMapping("/login")
    public String login(@RequestBody UserModel userModel){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword())
        );
        if(authentication.isAuthenticated()){
            System.out.println("Successfully authenticated");
            String token = jwtUtils01.generateToken(userModel);
            return "Token:" + token;
        }
        return "authentication failed";
    }

    @GetMapping("/hello")
    public String helloWorld(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return authentication.getName() + " is accessing the endpoint";
    }


}
