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

    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
