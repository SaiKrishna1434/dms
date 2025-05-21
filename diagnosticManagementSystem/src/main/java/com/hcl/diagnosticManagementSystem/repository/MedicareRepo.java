package com.hcl.diagnosticManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.Medicare;
@Repository
public interface MedicareRepo extends JpaRepository<Medicare, Integer>{

	@Query("SELECT MC FROM Medicare MC WHERE MC.serviceName LIKE %:searchKeyValue%")
	List<Medicare> findBySearchKeyValue(@Param("searchKeyValue") String searchKeyValue);
}
