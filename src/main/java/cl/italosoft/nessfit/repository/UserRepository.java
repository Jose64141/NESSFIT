package cl.italosoft.nessfit.repository;

import cl.italosoft.nessfit.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for User Entity
 */
public interface UserRepository extends JpaRepository<User, String>
{
    /**
     * Get a collection of Users filtered by String contained in Rut and ordered by their Role, all of it
     * grouped in a Page
     * @param rut rut or part of them to search
     * @param role role index to search
     * @param page type of page to group
     * @return Page of users
     */
    Page<User> findByRutContainingAndRoleIdOrderByIsEnabledDesc(String rut, int role, Pageable page);

    /**
     * Find a Page of users by their Role and if it's enabled, ordered desc.
     * @param role role index to search
     * @param page type of page to group
     * @return Page of users
     */
    Page<User> findByRoleIdOrderByIsEnabledDesc(int role, Pageable page);

    /**
     * Find a user by their rut and role index
     * @param rut rut of the user to search
     * @param role_id role index of the user to search
     * @return User if it got found, null if not
     */
    User findByRutAndRole_id(String rut, int role_id);

    /**
     * Find a user by their email
     * @param email email of the user to search
     * @return User if it got found, null if not
     */
    User findByEmail(String email);
}