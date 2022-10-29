package cl.italosoft.nessfit.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Model for SportsFacility Entity
 */
@Entity
@Table(name = "sportsfacilitys")
public class SportsFacility implements Serializable
{
    //@Name
    @NotBlank
    @Id
    private String name;
    //@Address
    @NotBlank
    private String address;     
    //@Type
    @NotBlank
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id",referencedColumnName = "id")
    private Type type;
    //@Cost
    @NotBlank
    @Min(value = 1000, message = "El costo por dia ingresado no es válido. Tiene que ser mayor o igual a 1000")
    @Max(value = 999999999, message = "El costo por dia ingresado no es válido.")
    @Column(name = "cost_day")
    private int cost;
    //@Status
    @NotBlank
    @Column(name = "status")
    private boolean status;

    /**
     * Full Constructor
     * @param name Name of sportsfacility
     * @param address Address of sportsfacility
     * @param type Type of sportsfacility
     * @param cost Cost of sportsfacility
     * @param status Status status of sportsfacility
     */
    public SportsFacility(String name, String address, Type type, int cost, boolean status)
    {
        this.name = name;
        this.address = address;
        this.type = type;
        this.cost = cost;
        this.status = status;

    }

    /**
     * Empty Constructor, initialize String as null, int as -1 and boolean as false
     */
    public SportsFacility()
    {
        this.name = null;
        this.address = null;
        this.type = null;
        this.cost = -1;
        this.status = false;

    }

    /**
     * Gets the sportsfacilityname
     * @return Current sportsfacilityname
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the sportsfacilityname
     * @param name New name for sportsfacility
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the sportsfacilityaddress
     * @return Current sportsfacilityaddress
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets the sportsfacilityaddress
     * @param address New address for sportsfacility
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Gets the sportsfacility type
     * @return sportsfacility type
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Sets the sportsfacility type
     * @param tyoe New sportsfacility type
     */
    public void setType(Type type)
    {
        this.type = type;
    }

    /**
     * Gets the sportsfacility phone number
     * @return Current sportsfacility phone number
     */
    public int getCost()
    {
        return cost;
    }

    /**
     * Sets the sportsfacility cost for day
     * @param cost New sportsfacility cost for day
     */
    public void setCost(int cost)
    {
        this.cost = cost;
    }

    /**
     * Gets the sportsfacility enabled status
     * @return sportsfacility enabled status
     */
    public boolean Status()
    {
        return status;
    }

    /**
     * Sets the sportsfacility enabled status
     * @param status New enabled status
     */
    public void setStatus(boolean status)
    {
        this.status = status;
    }
}

