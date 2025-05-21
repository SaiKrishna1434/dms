package com.hcl.diagnosticManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.diagnosticManagementSystem.entity.Medicare;
import com.hcl.diagnosticManagementSystem.exception.ValueNotFoundException;
import com.hcl.diagnosticManagementSystem.service.MedicareService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicareController {
	
	@Autowired
	private MedicareService medserv;
	
	@PostMapping("/save")
	public Medicare addService(@RequestBody Medicare med) {
		return medserv.addService(med);
	}
	
	@GetMapping("/getAll")
	public List<Medicare> getAllService(){
		return medserv.getAllService();
	}
	
	@GetMapping("/getServiceById/{id}")
	public Medicare getServiceById(@PathVariable int id) {
		return medserv.getServiceById(id);
	}
	
	@GetMapping("/searchByName")
	public List<Medicare> getServiceByName(@RequestParam("searchKeyValue") String searchKeyValue) {
		return medserv.getServiceByName(searchKeyValue);
	}
	
	@PutMapping("/update/{id}")
	public String updateServiceById(@PathVariable int id,@RequestBody Medicare med) throws ValueNotFoundException {
		return medserv.updateServiceById(id, med);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable int id) {
		return medserv.deleteServiceById(id);
	}
	
	@DeleteMapping("/delete")
	public String clearAll() {
		return medserv.clearAllService();
	}
	
	

}
