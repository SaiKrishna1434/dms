package healthcheckup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.diagnosticManagementSystem.entity.HealthCheckup;


@Repository
public interface HealthCheckupRepository extends JpaRepository<HealthCheckup, Long> {
	List<HealthCheckup> findByNameContaining(String name);
}
