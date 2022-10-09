package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for User Entity
 */
public interface UserRepository extends JpaRepository<User, String>
{
    User findByRutAndRole_id(String rut, int role_id);
}