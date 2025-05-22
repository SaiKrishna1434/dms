package healthcheckup.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerRegistrationRequestDTO {
	
	@NotBlank(message = "User ID is required")
	private String userId;
	
	@NotBlank(message = "Password is required")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password must contain at least one number, one lowercase letter, one uppercase letter, and be atleast 8 characters long.")
	private String password;
	
	@NotNull(message = "Date of Birth is required")
	private LocalDate dateOfBirth;
	
	@NotBlank(message =  "Email ID is required")
	@Email(message = "Invalid email format")
	private String emailId;
	
	// Constructors
	public CustomerRegistrationRequestDTO() {
	}
	
	public CustomerRegistrationRequestDTO(String userId, String password, LocalDate dateOfBirth, String emailId) {
		this.userId = userId;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.emailId = emailId;
	}

	// Getters and Setters
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}	
}
