package healthcheckup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.Customer;
import com.hcl.diagnosticManagementSystem.entity.CustomerHealthCheckupApplication;
import com.hcl.diagnosticManagementSystem.entity.HealthCheckup;

@Repository
public interface CustomerHealthCheckupApplicationRepository extends JpaRepository<CustomerHealthCheckupApplication, Long> {
	// Custom methods
	List<CustomerHealthCheckupApplication> findByCustomer(Customer customer);
    boolean existsByCustomerAndHealthCheckup(Customer customer, HealthCheckup healthCheckup);
}
