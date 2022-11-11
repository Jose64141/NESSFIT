package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.DeportiveCenter;
import cl.italosoft.nessfit.repository.DeportiveCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeportiveCenterServiceImpl implements DeportiveCenterService
{
    @Autowired
    private DeportiveCenterRepository deportiveCenterRepository;

    /**
     * Find a deportive center by their name
     * @param deportiveCenterName the id of the record to find.
     * @return DeportiveCenter if exists, null if not
     */
    @Override
    public DeportiveCenter find(String deportiveCenterName)
    {
        java.util.Optional<DeportiveCenter> sportsfacility = this.deportiveCenterRepository.findById(deportiveCenterName);
        return sportsfacility.orElse(null);
    }

    /**
     * Find a deportive center by their name and type
     * @param deportiveCenterName the id of the record to find
     * @param type the type of the record to find
     * @return DeportiveCenter if exists, null if not
     */
    @Override
    public DeportiveCenter find(String deportiveCenterName, int type)
    {
        return this.deportiveCenterRepository.findByNameAndType_id(deportiveCenterName,type);
    }

    /**
     * Find a deportive center by their address
     * @param deportiveCenterName the id of the record to find
     * @param address the address of the record to find
     * @return DeportiveCenter if exists, null if not
     */
    @Override
    public DeportiveCenter findByNameOrAddress(String deportiveCenterName, String address) {
        return this.deportiveCenterRepository.findByNameOrAddress(deportiveCenterName, address);
    }

    /**
     * Lists all deportive centers or find a record by its name.
     * @param name  the id of the record to find
     * @param page the pagination information
     * @return page containing the records.
     */
    @Override
    public Page<DeportiveCenter> findByName(String name, Pageable page)
    {
        if(name != null)
            return this.deportiveCenterRepository.findByNameContaining(name, page);
        else
            return deportiveCenterRepository.findAll(page);
    }

    /**
     * Saves a given DeportiveCenter record.
     * @param deportiveCenter the record to save.
     */
    @Override
    public void save(DeportiveCenter deportiveCenter)
    {
    	deportiveCenterRepository.save(deportiveCenter);
    }

    /**
     * Saves a given DeportiveCenter record and flushes the change instantly.
     * @param deportiveCenter the record to save.
     */
    @Override
    public void saveAndFlush(DeportiveCenter deportiveCenter) { deportiveCenterRepository.saveAndFlush(deportiveCenter); }

    /**
     * Deletes a DeportiveCenter record.
     * @param deportiveCenterName the id of the record to delete.
     */
    @Override
    public void delete(String deportiveCenterName)
    {
    	deportiveCenterRepository.deleteById(deportiveCenterName);
    }

    /**
     * Returns all DeportiveCenter records.
     * @return a list containing the records.
     */
    @Override
    public List<DeportiveCenter> list()
    {
        return deportiveCenterRepository.findAll();
    }


    /**
     * Flushes all the changes to the database.
     */
    @Override
    public void flush() { deportiveCenterRepository.flush(); }
}
