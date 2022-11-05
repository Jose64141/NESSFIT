package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.RentRequest;

import java.sql.Date;
import java.util.List;

/**
 * Interface for the RentRequest Service
 */
public interface

RentRequestService
{
    /**
     * Find a rent request by its id
     *
     * @param requestId the id of the record to find.
     * @return RentRequest if exists, null if not
     */
    public RentRequest find(int requestId);


    /**
     * Saves a given rent request record.
     *
     * @param rentRequest the rent request to save.
     */
    public void save(RentRequest rentRequest);

    /**
     * Saves a given rent request record and flushes the change instantly.
     *
     * @param rentRequest the rent request to save.
     */
    public void saveAndFlush(RentRequest rentRequest);

    /**
     * Deletes a rent request record.
     *
     * @param requestId the id of the record to delete.
     */
    public void delete(int requestId);

    /**
     * Returns all rent request records associated to the given user.
     * @param userRut The user rut.
     * @return a list containing the records.
     */
    public List<RentRequest> listByUser(String userRut);


    public List<RentRequest> listByDeportiveCenter(String deportiveCenterName);
    /**
     * Returns all dates of rent request records associated to the given deportive center, in the next 7 days from current date.
     *
     * @param deportiveCenterName The deportive center name.
     * @return a list containing the records.
     */
    public List<Date> listDeportiveCenterDates(String deportiveCenterName);

    /**
     * Flushes all the changes to the database.
     */
    public void flush();
}