package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for RentRequest Entity
 */
public interface RentRequestRepository extends JpaRepository<RentRequest, Integer>
{
    List<RentRequest> findByUser_Rut(String userRut);
    List<RentRequest> findByDeportive_Center_Name(String deportiveCenterName);
}
