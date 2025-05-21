package com.hcl.dmms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.dmms.entity.Customer;

public interface UserRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByUserId(String userId);
	
	@Query(value = "SELECT * FROM user i WHERE i.userName= ?1", nativeQuery = true)
	Optional<org.springframework.security.core.userdetails.User> findByUserName(String userName);

}
