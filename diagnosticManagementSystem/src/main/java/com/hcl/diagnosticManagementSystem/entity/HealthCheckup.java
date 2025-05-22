package healthcheckup.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "health_checkup")
public class HealthCheckup {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 255, message = "Name cannot exceed 255 characters")
	private String name;
	
	@Size(max = 1000, message = "Description cannot exceed 1000 characters")
	private String description;
	
	@NotBlank(message = "Eligibility cannot be blank")
	@Size(max = 50, message = "Character limit exceeded from 25 characters")
	private String eligibility;
	
	@NotBlank(message = "Frequency cannot be blank")
	@Size(max = 20, message = "Character limit exceeded from 25 characters")
	private String frequency;
	
	@Positive(message = "Cost should be a positive value")
    private double cost;
	

	@JsonIgnore 
	@OneToOne(mappedBy = "healthCheckup", cascade = CascadeType.ALL )
    private CustomerHealthCheckupApplication customerHealthCheckupApplication;

	public HealthCheckup() {}
	public HealthCheckup(Long id, String name, String description, String eligibility, String frequency, double cost,
			List<MedicareService> medicareService, CustomerHealthCheckupApplication customerHealthCheckupApplication) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.eligibility = eligibility;
		this.frequency = frequency;
		this.cost = cost;
		this.customerHealthCheckupApplication = customerHealthCheckupApplication;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public String getEligibility() { return eligibility; }
	public void setEligibility(String eligibility) { this.eligibility = eligibility; }
	
	public String getFrequency() { return frequency; }
	public void setFrequency(String frequency) { this.frequency = frequency; }
	
	public double getCost() {	return cost; }
	public void setCost(double cost) { this.cost = cost; }
	
	public CustomerHealthCheckupApplication getCustomerHealthCheckupApplication() { return customerHealthCheckupApplication; }
	public void setCustomerHealthCheckupApplication(CustomerHealthCheckupApplication customerHealthCheckupApplication) { this.customerHealthCheckupApplication = customerHealthCheckupApplication; }
}


