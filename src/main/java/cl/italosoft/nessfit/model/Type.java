package cl.italosoft.nessfit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Model for Type Entity
 */
@Entity
@Table(name = "deportive_center_types")
public class Type implements Serializable
{
    @Id
    private int id;
    private String name;

    /**
     * Full constructor
     * @param id Identification of type
     * @param name Name of type
     */
    public Type(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Empty constructor, initialize String as null or int as -1
     */
    public Type()
    {
        this.id = -1;
        this.name = null;
    }

    /**
     * Constructor, initialize name as null
     * @param id Identification of type
     */
    public Type(int id)
    {
        this.id = id;
        this.name = null;
    }

    /**
     * Gets the type id
     * @return Current type id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the type id
     * @param id New type id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets the type name
     * @return Current type name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the type name
     * @param name New type name
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
