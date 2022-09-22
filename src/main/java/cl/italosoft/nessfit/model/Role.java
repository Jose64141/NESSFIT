package cl.italosoft.nessfit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Model for Role Entity
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable
{
    @Id
    private int id;
    private String name;

    /**
     * Gets the role id
     * @return Current role id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the role id
     * @param id New role id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets the role name
     * @return Current role name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the role name
     * @param name New role name
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
