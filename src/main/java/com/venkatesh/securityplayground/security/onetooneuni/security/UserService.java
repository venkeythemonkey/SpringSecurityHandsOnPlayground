package com.venkatesh.securityplayground.security.onetooneuni.security;

import com.venkatesh.securityplayground.security.onetooneuni.repo.UserModelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserModelRepo userModelRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userModelRepo.findByUsername(username);
        return userDetails;
    }
}
