package cl.italosoft.nessfit.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model for Role Entity
 */
@Entity
@Table(name = "roles")
public class Role 
{
    @Id
    private int id;
    private String name;
}
