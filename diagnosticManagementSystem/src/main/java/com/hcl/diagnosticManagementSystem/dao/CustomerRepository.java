package com.hcl.diagnosticManagementSystem.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByUserId(String userId);
	Optional<Customer> findByEmailId(String emailId);
}
