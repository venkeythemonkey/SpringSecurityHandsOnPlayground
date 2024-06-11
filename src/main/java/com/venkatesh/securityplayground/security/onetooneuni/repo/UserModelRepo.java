package com.venkatesh.securityplayground.security.onetooneuni.repo;

import com.venkatesh.securityplayground.security.onetooneuni.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepo extends JpaRepository<UserModel, Integer> {
    UserDetails findByUsername(String username);
}
