package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    /**
     * Find a user by their rut
     *
     * @param userRut the id of the record to find.
     * @return User if exists, null if not
     */
    @Override
    public User find(String userRut)
    {
        java.util.Optional<User> user = this.userRepository.findById(userRut);
        return user.orElse(null);
    }

    /**
     * Find a user by their rut and role
     *
     * @param userRut the id of the record tro find
     * @param role    the role of the user to find
     * @return User if exists, null if not
     */
    @Override
    public User find(String userRut, int role)
    {
        return this.userRepository.findByRutAndRole_id(userRut,role);
    }

    /**
     * Find a user by their email
     * @param userRut the id of the record tro find
     * @param userEmail the email of the record to find
     * @return User if exists, null if not
     */
    @Override
    public User findByRutOrEmail(String userRut,String userEmail) {
        return this.userRepository.findByRutOrEmail(userRut, userEmail);
    }

    /**
     * List or find a user by their rut and role
     * @param userRut the id of the record to find
     * @param page    the pagination information
     * @param role    the role id of users to find
     * @return Page with the records
     */
    @Override
    public Page<User> findByRutWithRole(String userRut, Pageable page, int role) {
        return this.userRepository.findByRutContainingAndRoleIdOrderByIsEnabledDesc(userRut,role,page);
    }

    /**
     * Saves a given user record.
     * @param user the user to save.
     */
    @Override
    public void save(User user)
    {
        userRepository.save(user);
    }

    /**
     * Saves a given user record and flushes the change instantly.
     * @param user the user to save.
     */
    @Override
    public void saveAndFlush(User user) { userRepository.saveAndFlush(user); }

    /**
     * Deletes a user record.
     * @param userRut the id of the record to delete.
     */
    @Override
    public void delete(String userRut)
    {
        userRepository.deleteById(userRut);
    }

    /**
     * Returns all user records.
     * @return a list containing the records.
     */
    @Override
    public List<User> list()
    {
        return userRepository.findAll();
    }

    /**
     * Flushes all the changes to the database.
     */
    @Override
    public void flush() { userRepository.flush(); }
}
