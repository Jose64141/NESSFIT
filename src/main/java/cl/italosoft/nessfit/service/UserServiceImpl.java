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
