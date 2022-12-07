package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.repository.RentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RentRequestServiceImpl implements RentRequestService
{
    @Autowired
    private RentRequestRepository rentRequestRepository;
    /**
     * Find a rent request by its id
     *
     * @param requestId the id of the record to find.
     * @return RentRequest if exists, null if not
     */
    @Override
    public RentRequest find(int requestId)
    {
        java.util.Optional<RentRequest> rentRequest = this.rentRequestRepository.findById(requestId);
        return rentRequest.orElse(null);
    }

    /**
     * List all the requests with the given status
     * @param status the status of the records to find
     * @param page the pagination information
     * @return page containing the records.
     */
    public Page<RentRequest> findByStatus(String status, Pageable page)
    {
        return rentRequestRepository.findByStatusOrderByRequestDateAsc(status, page);
    }

    /**
     * Saves a given rent request record.
     *
     * @param rentRequest the rent request to save.
     */
    @Override
    public RentRequest save(RentRequest rentRequest) { return rentRequestRepository.save(rentRequest);}

    /**
     * Saves a given rent request record and flushes the change instantly.
     *
     * @param rentRequest the rent request to save.
     */
    @Override
    public RentRequest saveAndFlush(RentRequest rentRequest) { return rentRequestRepository.saveAndFlush(rentRequest);}

    /**
     * Deletes a rent request record.
     *
     * @param requestId the id of the record to delete.
     */
    @Override
    public void delete(int requestId) { rentRequestRepository.deleteById(requestId);}

    /**
     * Returns all rent request records associated to the given user.
     *
     * @param userRut The user rut.
     * @return a list containing the records.
     */
    @Override
    public List<RentRequest> listByUser(String userRut) { return this.rentRequestRepository.findByUser_Rut(userRut); }

    /**
     * Returns all rent request records associated to the given deportive center.
     *
     * @param deportiveCenterName The deportive center name.
     * @return a list containing the records.
     */
    @Override
    public List<RentRequest> listByDeportiveCenter(String deportiveCenterName) { return this.rentRequestRepository.findByDeportiveCenter(deportiveCenterName); }

    /**
     * Returns all dates of rent request records associated to the given deportive center, in the next 7 days from current date.
     *
     * @param deportiveCenterName The deportive center name.
     * @return a list containing the records.
     */
    @Override
    public List<Date> listDeportiveCenterDates(String deportiveCenterName) { return this.rentRequestRepository.findDeportiveCenterDates(deportiveCenterName); }

    /**
     * Flushes all the changes to the database.
     */
    @Override
    public void flush() { rentRequestRepository.flush(); }

    /**
     * Returns all rent requests
     *
     * @return a list containing all rent requests.
     */
	@Override
	public List<RentRequest> findAll() { return this.rentRequestRepository.findAll();}

	@Override
	public Page<RentRequest> findRentRequestByUser(String userRut, Pageable page)
	{
		return this.rentRequestRepository.findRentRequestByUserRut(userRut, page);
	}

}