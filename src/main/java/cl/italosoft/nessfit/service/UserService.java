package cl.italosoft.nessfit.service;


import cl.italosoft.nessfit.model.User;

import java.util.List;

/**
 * Interface for the User Service
 */
public interface UserService
{

    /**
     * Saves a given user record.
     * @param user the user to save.
     */
    public void save(User user);

    /**
     * Deletes a user record.
     * @param userRut the id of the record to delete.
     */
    public void delete(String userRut);

    /**
     * Returns all user records.
     * @return a list containing the records.
     */
    public List<User> list();
}
