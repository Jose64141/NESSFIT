package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>
{
}
