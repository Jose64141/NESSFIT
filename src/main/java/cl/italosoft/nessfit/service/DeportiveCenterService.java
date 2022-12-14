package cl.italosoft.nessfit.service;


import cl.italosoft.nessfit.model.DeportiveCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface for the SportsFacility Service
 */
public interface DeportiveCenterService
{
    /**
     * Find a deportive center by their name
     * @param deportiveCenterName the id of the record to find.
     * @return DeportiveCenter if exists, null if not
     */
    public DeportiveCenter find(String deportiveCenterName);

    /**
     * Find a deportive center by their name and type
     * @param deportiveCenterName the id of the record to find
     * @param type the type of the record to find
     * @return DeportiveCenter if exists, null if not
     */
    public DeportiveCenter find(String deportiveCenterName, int type);

    /**
     * Find a deportive center by their address
     * @param deportiveCenterName the id of the record to find
     * @param address the address of the record to find
     * @return DeportiveCenter if exists, null if not
     */
    public DeportiveCenter findByNameOrAddress(String deportiveCenterName, String address);

    /**
     * Lists all deportive centers or find a record by its name.
     * @param name  the id of the record to find
     * @param page the pagination information
     * @return page containing the records.
     */
    public Page<DeportiveCenter> findByName(String name, Pageable page);

    /**
     * Saves a given DeportiveCenter record.
     * @param deportiveCenter the record to save.
     * @return 
     */
    public DeportiveCenter save(DeportiveCenter deportiveCenter);

    /**
     * Saves a given DeportiveCenter record and flushes the change instantly.
     * @param deportiveCenter the record to save.
     * @return 
     */
    public DeportiveCenter saveAndFlush(DeportiveCenter deportiveCenter);

    /**
     * Deletes a DeportiveCenter record.
     * @param deportiveCenterName the id of the record to delete.
     */
    public void delete(String deportiveCenterName);

    /**
     * Returns all DeportiveCenter records.
     * @return a list containing the records.
     */
    public List<DeportiveCenter> list();

    /**
     * Returns all DeportiveCenter records which are enabled.
     * @return a list containing the records.
     */
    public List<DeportiveCenter> listEnabled();

    /**
     * Flushes all the changes to the database.
     */
    public void flush();
}
