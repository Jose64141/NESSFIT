package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.DeportiveCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for DeportiveCenter Entity
 */
public interface DeportiveCenterRepository extends JpaRepository<DeportiveCenter, String>
{
	DeportiveCenter findByNameAndType_id(String name, int type_id);
	DeportiveCenter findByNameOrAddress(String name, String address);
	List<DeportiveCenter> findByIsEnabledTrue();
}