package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    /*
    @Query(value = "SELECT r.request_date, r.id, r.deportive_center_name, r.total_price, r.user_rut, u.name, r.request_status" +
    		"FROM rent_requests r INNER JOIN users u ON r.user_rut = u.rut INNER JOIN deportive_centers c ON r.deportive_center_name = c.name " +
    		"FROM RentRequest r INNER JOIN User u ON r.user_rut = u.rut INNER JOIN DeportiveCenter c ON r.deportive_center_name = c.name " +
    		"AND r.user_rut = ?1", nativeQuery = true)
    		*/
	Page<RentRequest> findRentRequestByUserRut(String userRut, Pageable page); 
    
}
