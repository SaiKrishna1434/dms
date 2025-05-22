package com.hcl.diagnosticManagementSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	Optional<Customer> findById(String id);
	Optional<Customer> findByUserId(String userId);
	Optional<Customer> findByEmailId(String emailId);

}
