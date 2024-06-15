package com.venkatesh.securityplayground.security.onetomanyuni.repo;

import com.venkatesh.securityplayground.security.onetomanyuni.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
}
