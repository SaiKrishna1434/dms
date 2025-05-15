package com.example.dmcmsproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dmcmsproject.model.Customer;
import com.example.dmcmsproject.model.CustomerServiceRecord;
import com.example.dmcmsproject.response.CustomerResponse;

@Repository
public interface CustomerServiceRecordsRepository extends JpaRepository<CustomerServiceRecord, Long>{

	public void save(Customer optionalCustomer);

}
