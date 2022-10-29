package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.DeportiveCenter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for User Entity
 */
public interface SportsFacilityRepository extends JpaRepository<DeportiveCenter, String>
{
	DeportiveCenter findByNameAndType_id(String name, int type_id);
	DeportiveCenter findByNameOrAddress(String name, String address);
}