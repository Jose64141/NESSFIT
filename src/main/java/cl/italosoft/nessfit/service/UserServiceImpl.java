package cl.italosoft.nessfit.service;

import cl.italosoft.nessfit.model.User;
import cl.italosoft.nessfit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        java.util.Optional<User>  user = this.userRepository.findById(userRut);
        return user.orElse(null);
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
}
