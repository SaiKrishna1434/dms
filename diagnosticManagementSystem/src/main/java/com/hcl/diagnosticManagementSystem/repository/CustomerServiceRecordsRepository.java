package com.hcl.diagnosticManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.CustomerServiceRecord;

@Repository
public interface CustomerServiceRecordsRepository extends JpaRepository<CustomerServiceRecord, Long>{

	public void save(Customer optionalCustomer);

}
