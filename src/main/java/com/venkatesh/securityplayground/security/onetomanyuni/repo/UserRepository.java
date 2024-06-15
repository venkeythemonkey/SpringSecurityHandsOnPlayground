package com.venkatesh.securityplayground.security.onetomanyuni.repo;

import com.venkatesh.securityplayground.security.onetomanyuni.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserDetails findByUsername(String username);
}
