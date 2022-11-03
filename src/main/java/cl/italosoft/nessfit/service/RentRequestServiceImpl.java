package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.RentRequest;
import cl.italosoft.nessfit.repository.RentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
     * Saves a given rent request record.
     *
     * @param rentRequest the rent request to save.
     */
    @Override
    public void save(RentRequest rentRequest) { rentRequestRepository.save(rentRequest);}

    /**
     * Saves a given rent request record and flushes the change instantly.
     *
     * @param rentRequest the rent request to save.
     */
    @Override
    public void saveAndFlush(RentRequest rentRequest) { rentRequestRepository.saveAndFlush(rentRequest);}

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
    public List<RentRequest> listByDeportiveCenter(String deportiveCenterName) { return this.rentRequestRepository.findByDeportive_Center_Name(deportiveCenterName); }

    /**
     * Flushes all the changes to the database.
     */
    @Override
    public void flush() { rentRequestRepository.flush(); }
}
