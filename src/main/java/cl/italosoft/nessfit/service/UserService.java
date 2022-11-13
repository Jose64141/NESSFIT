package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param userRut the id of the record to find
     * @param role the role of the user to find
     * @return User if exists, null if not
     */
    public User find(String userRut,int role);

    /**
     * Find a user by their email
     * @param userEmail the email of the record to find
     * @return User if exists, null if not
     */
    public User findByEmail(String userEmail);

    /**
     * List or find a user by their rut and role
     * @param userRut the id of the record to find
     * @param page the pagination information
     * @param role the role id of users to find
     * @return Page with the records
     */
    public Page<User> findByRutWithRole(String userRut, Pageable page, int role);


    /**
     * Saves a given user record.
     * @param user the user to save.
     * @return Saved user
     */
    public User save(User user);

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
