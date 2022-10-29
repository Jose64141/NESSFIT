package cl.italosoft.nessfit.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Model for SportsFacility Entity
 */
@Entity
@Table(name = "deportive_centers")
public class DeportiveCenter implements Serializable
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
    @Min(value = 999, message = "El costo por dia ingresado no es válido. Tiene que ser mayor o igual a 1000")
    @Max(value = 999999999, message = "El costo por dia ingresado no es válido.")
    @Column(name = "cost_per_day")
    private int costPerDay;
    //@Status
    @NotBlank
    @Column(name = "is_enabled")
    private boolean isEnabled;

    /**
     * Full Constructor
     * @param name Name of DeportiveCenter
     * @param address Address of DeportiveCenter
     * @param type Type of DeportiveCenter
     * @param costPerDay Cost of DeportiveCenter
     * @param isEnabled if DeportiveCenter is enabled
     */
    public DeportiveCenter(String name, String address, Type type, int costPerDay, boolean isEnabled)
    {
        this.name = name;
        this.address = address;
        this.type = type;
        this.costPerDay = costPerDay;
        this.isEnabled = isEnabled;

    }

    /**
     * Empty Constructor, initialize String as null, int as -1 and boolean as false
     */
    public DeportiveCenter()
    {
        this.name = null;
        this.address = null;
        this.type = null;
        this.costPerDay = -1;
        this.isEnabled = false;

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
     * @param type New sportsfacility type
     */
    public void setType(Type type)
    {
        this.type = type;
    }

    /**
     * Gets the cost per day
     * @return Integer with cost per day
     */
    public int getCostPerDay()
    {
        return costPerDay;
    }

    /**
     * Sets the cost per day
     * @param costPerDay new cost per day
     */
    public void setCostPerDay(int costPerDay)
    {
        this.costPerDay = costPerDay;
    }

    /**
     * Gets the enabled status
     * @return True if its enabled, False is not
     */
    public boolean isEnabled()
    {
        return isEnabled;
    }

    /**
     * Sets the enabled status
     * @param enabled new enabled status
     */
    public void setEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }

}

