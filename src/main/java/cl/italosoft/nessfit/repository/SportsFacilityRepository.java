package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.SportsFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for User Entity
 */
public interface SportsFacilityRepository extends JpaRepository<SportsFacility, String>
{
	SportsFacility findByNameAndType_id(String name, int type_id);
	SportsFacility findByNameOrAddress(String name, String address);
}