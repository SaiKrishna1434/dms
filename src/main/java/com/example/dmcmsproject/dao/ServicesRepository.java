package com.example.dmcmsproject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dmcmsproject.model.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services,Long>  {

	public List<Services> findByIdIn(List<String> serviceNames);

	public List<Services> findByServiceIn(List<String> serviceNames);

}
