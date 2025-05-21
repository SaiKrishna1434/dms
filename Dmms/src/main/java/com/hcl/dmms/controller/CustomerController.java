package com.hcl.dmms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CustomerController {

//	@Autowired
//	private UserLoginServices customerService;

	@GetMapping("/home")
	public String showHomePage() {
		return "index"; // Will render /WEB-INF/views/register.jsp
	}

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register"; // Will render /WEB-INF/views/register.jsp
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login"; // This maps to /WEB-INF/views/login.jsp
	}

	@GetMapping("/searchService")
	public String showMedicareServicesPage() {
		return "searchService"; // Will render /WEB-INF/views/register.jsp
	}

	@GetMapping("/applyCheckup")
	public String showhealthCheckupServicesPage() {
		return "applyCheckup"; // Will render /WEB-INF/views/register.jsp
	}

	@GetMapping("/insuranceAgent")
	public String showinsuranceAgentPage() {
		return "insuranceAgent"; // Will render /WEB-INF/views/register.jsp
	}
	
	
	@GetMapping("/viewReport")
	private String showReportPage() {
	    return "viewReport";
	}

}