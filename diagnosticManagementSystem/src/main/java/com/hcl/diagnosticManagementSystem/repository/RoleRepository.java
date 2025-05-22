package com.hcl.diagnosticManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.diagnosticManagementSystem.entity.Role;
import com.hcl.diagnosticManagementSystem.entity.Role.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(RoleName name);
}
