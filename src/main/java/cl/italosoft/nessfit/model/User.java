package cl.italosoft.nessfit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model for User Entity
 */
@Entity
@Table(name = "users")
public class User
{
    @Id
    private String rut;
    private String name;
    private String lastName;
    private String password;
    private int phoneNumber;
    private String email;
    /**
     * True: Enabled, False: Disabled
     */
    private boolean status;
    private Role role;
}
