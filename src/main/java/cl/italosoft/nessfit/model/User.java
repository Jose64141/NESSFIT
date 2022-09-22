package cl.italosoft.nessfit.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Model for User Entity
 */
@Entity
@Table(name = "users")
public class User implements Serializable
{
    @Id
    private String rut;
    private String name;
    private String lastName;
    private String password;
    private int phoneNumber;
    private String email;
    private boolean isEnabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;

    /**
     * Full Constructor
     * @param rut Rut of user
     * @param name Name of user
     * @param lastName Last name of user
     * @param password Password of user
     * @param phoneNumber Phone number of user
     * @param email Email  of user
     * @param isEnabled Enables status of user
     * @param role Role of user
     */
    public User(String rut, String name, String lastName, String password, int phoneNumber,
                String email, boolean isEnabled, Role role)
    {
        this.rut = rut;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isEnabled = isEnabled;
        this.role = role;
    }

    /**
     * Empty Constructor, initialize String as null, int as -1 and boolean as false
     */
    public User()
    {
        this.rut = null;
        this.name = null;
        this.lastName = null;
        this.password = null;
        this.phoneNumber = -1;
        this.email = null;
        this.isEnabled = false;
        this.role = null;
    }

    /**
     * Gets the user rut
     * @return Current user rut
     */
    public String getRut()
    {
        return rut;
    }

    /**
     * Sets the user rut
     * @param rut New rut for user
     */
    public void setRut(String rut)
    {
        this.rut = rut;
    }

    /**
     * Gets the username
     * @return Current username
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the username
     * @param name New name for user
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the user lastname
     * @return Current user lastname
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the user lastname
     * @param lastName New lastname for user
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets the user password
     * @return Current user password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the user password
     * @param password New password for user
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets the user phone number
     * @return Current user phone number
     */
    public int getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Sets the user phone number
     * @param phoneNumber New user phone number
     */
    public void setPhoneNumber(int phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user e-mail
     * @return Current user e-mail
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the user e-mail
     * @param email New user e-mail
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Gets the user enabled status
     * @return user enabled status
     */
    public boolean isEnabled()
    {
        return isEnabled;
    }

    /**
     * Sets the user enabled status
     * @param isEnabled New enabled status
     */
    public void setEnabled(boolean isEnabled)
    {
        this.isEnabled = isEnabled;
    }

    /**
     * Gets the user role
     * @return user role
     */
    public Role getRole()
    {
        return role;
    }

    /**
     * Sets the user role
     * @param role New user role
     */
    public void setRole(Role role)
    {
        this.role = role;
    }
}
