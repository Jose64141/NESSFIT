package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.DeportiveCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for DeportiveCenter Entity
 */
public interface DeportiveCenterRepository extends JpaRepository<DeportiveCenter, String>
{
	/**
	 * Find a deportive center by their name and type index
	 * @param name name of the deportive center to search
	 * @param type_id type index of the deportive center to search
	 * @return Deportive center if it got found, null if not
	 */
	DeportiveCenter findByNameAndType_id(String name, int type_id);

	/**
	 * Find a deportive center by their name or address
	 * @param name name of the deportive center to search
	 * @param address address of the deportive center to search
	 * @return Deportive center if it got found, null if not
	 */
	DeportiveCenter findByNameOrAddress(String name, String address);

	/**
	 * Find all the deportive center enabled
	 * @return List of deportive centers enabled
	 */
	List<DeportiveCenter> findByIsEnabledTrue();

	/**
	 * Find all the deportive center with string coincident in their name
	 * @param name name coincident of the deportive center
	 * @param page type of page to group
	 * @return Page of deportive cneters
	 */
	Page<DeportiveCenter> findByNameContaining(String name, Pageable page);
}