package cl.italosoft.nessfit.service;


import cl.italosoft.nessfit.model.SportsFacility;

import java.util.List;

/**
 * Interface for the SportsFacility Service
 */
public interface SportsFacilityService
{
    /**
     * Find a sportsfacility by their name
     * @param SportsFacilityname the id of the record to find.
     * @return SportsFacility if exists, null if not
     */
    public SportsFacility find(String SportsFacilityName);

    /**
     * Find a sportsfacility by their SportsFacilityName and type
     * @param SportsFacilityName the id of the record to find
     * @param type the type of the sportsfacility to find
     * @return SportsFacility if exists, null if not
     */
    public SportsFacility find(String SportsFacilityName,int type);

    /**
     * Find a sportsfacility by their address
     * @param SportsFacilityName the id of the record tro find
     * @param address the address of the record to find
     * @return SportsFacility if exists, null if not
     */
    public SportsFacility findByNameOrAddress(String SportsFacilityName,String address);


    /**
     * Saves a given sportsfacility record.
     * @param sportsfacility the sportsfacility to save.
     */
    public void save(SportsFacility sportsfacility);

    /**
     * Saves a given sportsfacility record and flushes the change instantly.
     * @param sportsfacility the sportsfacility to save.
     */
    public void saveAndFlush(SportsFacility sportsfacility);

    /**
     * Deletes a sportsfacility record.
     * @param SportsFacilityName the id of the record to delete.
     */
    public void delete(String SportsFacilityName);

    /**
     * Returns all sportsfacility records.
     * @return a list containing the records.
     */
    public List<SportsFacility> list();

    /**
     * Flushes all the changes to the database.
     */
    public void flush();
}
