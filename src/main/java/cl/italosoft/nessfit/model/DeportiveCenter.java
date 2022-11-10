package cl.italosoft.nessfit.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Model for DeportiveCenter Entity
 */
@Entity
@Table(name = "deportive_centers")
public class DeportiveCenter implements Serializable
{
    @NotBlank
    @Id
    private String name;
    @NotBlank
    private String address;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id",referencedColumnName = "id")
    private Type type;
    @NotNull
    @Min(value = 1000, message = "El costo por dia ingresado no es válido. Tiene que ser mayor o igual a 1000")
    @Max(value = 999999999, message = "El costo por dia ingresado no es válido.")
    @Column(name = "cost_per_day")
    private Integer costPerDay;
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
        this.costPerDay = null;
        this.isEnabled = false;

    }

    /**
     * Gets the deportive center name
     * @return Current deportive center name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the deportive center name
     * @param name New name for the deportive center
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the deportive center address
     * @return Current deportive center address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets the deportive center address
     * @param address New address for the deportive center
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Gets the deportive center type
     * @return The deportive center type
     */
    public Type getType()
    {
        return type;
    }

    /**
     * Sets the deportive center type
     * @param type New deportive center type
     */
    public void setType(Type type)
    {
        this.type = type;
    }

    /**
     * Gets the cost per day
     * @return Integer with cost per day
     */
    public Integer getCostPerDay()
    {
        return costPerDay;
    }

    /**
     * Sets the cost per day
     * @param costPerDay new cost per day
     */
    public void setCostPerDay(Integer costPerDay)
    {
        this.costPerDay = costPerDay;
    }

    /**
     * Gets the enabled status
     * @return True if its enabled, False is not
     */
    public boolean getIsEnabled()
    {
        return isEnabled;
    }

    /**
     * Sets the enabled status
     * @param enabled new enabled status
     */
    public void setIsEnabled(boolean enabled)
    {
        isEnabled = enabled;
    }

}

