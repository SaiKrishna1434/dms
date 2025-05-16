package com.hcl.diagnosticManagementSystem.dao;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	// Custom finder method to search role by name
	Optional<Role> findByName(String name);
	
}
