package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.RentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * Repository for RentRequest Entity
 */
public interface RentRequestRepository extends JpaRepository<RentRequest, Integer>
{
    List<RentRequest> findByUser_Rut(String userRut);
    List<RentRequest> findByDeportiveCenter(String deportiveCenterName);
    List<RentRequest> findByRequestDateBetween(Date dateBeginning, Date dateEnd);

    /**
     * Find all the requested dates of a deportive center
     * @param deportiveCenterName Deportive center name
     * @return List of Dates
     */
    @Query(value = "SELECT rd.date " +
            "FROM rent_requests r INNER JOIN rent_dates rd ON r.id = rd.rent_id " +
            "WHERE rd.date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL 7 DAY " +
            "AND r.deportive_center_name = ?1", nativeQuery = true)
    List<Date> findDeportiveCenterDates(String deportiveCenterName);
}
