package cl.italosoft.nessfit.service;


import cl.italosoft.nessfit.model.User;

import java.util.List;

public interface UserService
{
    public void save(User user);

    public void delete(String userRut);

    public List<User> list();
}
