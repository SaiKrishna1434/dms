package com.hcl.diagnosticManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.diagnosticManagementSystem.entity.Medicare;
import com.hcl.diagnosticManagementSystem.exception.ValueNotFoundException;
import com.hcl.diagnosticManagementSystem.repository.MedicareRepo;

@Service
public class MedicareService {
	
	@Autowired
	private MedicareRepo medrepo;
	
	public Medicare addService(Medicare med) {
		return medrepo.save(med);
	}
	
	public List<Medicare> getAllService(){
		return medrepo.findAll();
	}
	
	public Medicare getServiceById(int id) {
		return medrepo.findById(id).orElse(new Medicare());
	}

	public List<Medicare> getServiceByName(String searchKeyValue){
		return medrepo.findBySearchKeyValue(searchKeyValue);
	}
	
	public String updateServiceById(int id,Medicare med) throws ValueNotFoundException{
		Medicare oldData = medrepo.findById(id).orElseThrow(()-> new ValueNotFoundException("Data Not Found"));
		
		oldData.setServiceName(med.getServiceName());
		medrepo.save(oldData);
		return "updaed Successfully";
	}
	
	public String deleteServiceById(int id) {
		medrepo.deleteById(id);
		return "deleted "+id+" service";
	}
	
	public String clearAllService() {
		medrepo.deleteAll();
		return "All services cleared";
	}
}
