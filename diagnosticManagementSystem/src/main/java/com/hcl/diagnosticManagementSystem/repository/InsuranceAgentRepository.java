package com.hcl.diagnosticManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.InsuranceAgent;

@Repository
public interface InsuranceAgentRepository extends JpaRepository<InsuranceAgent,Long>{

	@Query("select distinct a from InsuranceAgent a left join fetch a.services where a.location= :location")
	public List<InsuranceAgent> findByLocation(@Param("location")String location);

	public Optional<InsuranceAgent> findByName(String name);
	
	public List<InsuranceAgent> findAgentByName(String name);
	
	public Optional<InsuranceAgent> findById(Long id);
	
}
