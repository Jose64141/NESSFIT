package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.repository.SportsFacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportsFacilityServiceImpl implements SportsFacilityService
{
    @Autowired
    private SportsFacilityRepository sportsfacilityRepository;

    /**
     * Find a sportsfacility by their name
     *
     * @param SportsFacilityName the id of the record to find.
     * @return SportsFacility if exists, null if not
     */
    @Override
    public DeportiveCenter find(String SportsFacilityName)
    {
        java.util.Optional<DeportiveCenter> sportsfacility = this.sportsfacilityRepository.findById(SportsFacilityName);
        return sportsfacility.orElse(null);
    }

    /**
     * Find a sportsfacility by their name and type
     *
     * @param SportsFacilityName the id of the record tro find
     * @param type the type of the sportsfacility to find
     * @return SportsFacility if exists, null if not
     */
    @Override
    public DeportiveCenter find(String SportsFacilityName, int type)
    {
        return this.sportsfacilityRepository.findByNameAndType_id(SportsFacilityName,type);
    }

    /**
     * Find a sportsfacility by their address
     * @param SportsFacilityName the id of the record tro find
     * @param address the address of the record to find
     * @return SportsFacility if exists, null if not
     */
    @Override
    public DeportiveCenter findByNameOrAddress(String SportsFacilityName, String address) {
        return this.sportsfacilityRepository.findByNameOrAddress(SportsFacilityName, address);
    }

    /**
     * Saves a given sportsfacility record.
     * @param sportsfacility the sportsfacility to save.
     */
    @Override
    public void save(DeportiveCenter sportsfacility)
    {
    	sportsfacilityRepository.save(sportsfacility);
    }

    /**
     * Saves a given sportsfacility record and flushes the change instantly.
     * @param sportsfacility the sportsfacility to save.
     */
    @Override
    public void saveAndFlush(DeportiveCenter sportsfacility) { sportsfacilityRepository.saveAndFlush(sportsfacility); }

    /**
     * Deletes a sportsfacility record.
     * @param SportsFacilityName the id of the record to delete.
     */
    @Override
    public void delete(String SportsFacilityName)
    {
    	sportsfacilityRepository.deleteById(SportsFacilityName);
    }

    /**
     * Returns all sportsfacility records.
     * @return a list containing the records.
     */
    @Override
    public List<DeportiveCenter> list()
    {
        return sportsfacilityRepository.findAll();
    }

    /**
     * Flushes all the changes to the database.
     */
    @Override
    public void flush() { sportsfacilityRepository.flush(); }
}
