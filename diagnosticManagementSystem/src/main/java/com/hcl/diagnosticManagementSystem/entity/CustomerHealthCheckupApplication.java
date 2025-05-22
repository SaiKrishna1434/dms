package healthcheckup.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customer_health_checkup_application")
public class CustomerHealthCheckupApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Application date cannot be null")
	private LocalDate applicationDate;
	
	@NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status cannot exceed 50 characters")
	private String status;		// e.g., "Pending", "Approved", "Completed"
	
	@ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "health_checkup_id")
    private HealthCheckup healthCheckup;
	
	
	public CustomerHealthCheckupApplication() {}
	
	public CustomerHealthCheckupApplication(Long id, Customer customer, HealthCheckup healthCheckup,
		LocalDate applicationDate, String status) {
		this.id = id;
		this.customer = customer;
		this.healthCheckup = healthCheckup;
		this.applicationDate = applicationDate;
		this.status = status;
	}
	
	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public HealthCheckup getHealthCheckup() {
		return healthCheckup;
	}
	public void setHealthCheckup(HealthCheckup healthCheckup) {
		this.healthCheckup = healthCheckup;
	}

	public LocalDate getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
