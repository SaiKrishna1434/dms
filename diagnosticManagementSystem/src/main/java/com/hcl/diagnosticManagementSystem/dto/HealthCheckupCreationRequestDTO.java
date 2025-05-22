package healthcheckup.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class HealthCheckupCreationRequestDTO {
	
	@NotBlank(message = "Health checkup plan name is required")
	private String name;
	
	@NotBlank(message = "Health checkup description is required")
	private String description;
	
	@NotBlank(message = "Health checkup Eligibility cannot be blank")
	@Size(max = 50, message = "Character limit exceeded from 25 characters")
	private String eligibility;
	
	@NotBlank(message = "Health checkup Frequency cannot be blank")
	@Size(max = 25, message = "Character limit exceeded from 25 characters")
	private String frequency;
	
	@NotNull(message = "Health checkup cost is required")
	@Positive(message = "Health checkup cost should be positve")
	private double cost;
	
	
	public HealthCheckupCreationRequestDTO() {}
	
	public HealthCheckupCreationRequestDTO(String name, String description, String eligibility, String frequency, double cost, List<Long> medicareServiceIds) {
		this.name = name;
		this.description = description;
		this.eligibility = eligibility;
		this.frequency = frequency;
		this.cost = cost;
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getDescription() { return description;}
	public void setDescription(String description) { this.description = description; }
	
	public String getEligibility() { return eligibility; }
	public void setEligibility(String eligibility) { this.eligibility = eligibility; }
	
	public String getFrequency() { return frequency; }
	public void setFrequency(String frequency) { this.frequency = frequency; }
	
	public double getCost() { return cost; }
	public void setCost(double cost) { this.cost = cost; }
}
