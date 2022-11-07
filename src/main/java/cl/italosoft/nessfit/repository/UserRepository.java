package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for User Entity
 */
public interface UserRepository extends JpaRepository<User, String>
{
    Page<User> findByRutContainingAndRole_idOrderByIs_enabledDesc(String rut, int role, Pageable page);
    User findByRutAndRole_id(String rut, int role_id);
    User findByRutOrEmail(String rut, String email);
}