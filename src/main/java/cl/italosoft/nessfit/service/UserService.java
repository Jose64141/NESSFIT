package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.User;

import java.util.List;

/**
 * Interface for the User Service
 */
public interface UserService
{
    /**
     * Find a user by their rut
     * @param userRut the id of the record to find.
     * @return User if exists, null if not
     */
    public User find(String userRut);

    /**
     * Find a user by their rut and role
     * @param userRut the id of the record tro find
     * @param role the role of the user to find
     * @return User if exists, null if not
     */
    public User find(String userRut,int role);


    /**
     * Saves a given user record.
     * @param user the user to save.
     */
    public void save(User user);

    /**
     * Saves a given user record and flushes the change instantly.
     * @param user the user to save.
     */
    public void saveAndFlush(User user);

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

    /**
     * Flushes all the changes to the database.
     */
    public void flush();
}
