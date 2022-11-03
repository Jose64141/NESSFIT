package cl.italosoft.nessfit.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Model for RentRequest Entity
 */
@Entity
@Table(name = "rent_requests")
public class RentRequest implements Serializable
{
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_rut",referencedColumnName = "rut")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deportive_center_name",referencedColumnName = "name")
    private DeportiveCenter deportiveCenter;
    @Column(name="request_status")
    private String status;
    @Column(name="total_price")
    private int totalPrice;

    @ElementCollection
    @CollectionTable
    (
        name="rent_dates",
        joinColumns=@JoinColumn(name="rent_id", referencedColumnName="id")
    )
    @Column(name="date")
    private List<Date> dates; // Based on https://en.wikibooks.org/wiki/Java_Persistence/ElementCollection

    /**
     * Full Constructor
     * @param user The user that made the request
     * @param deportiveCenter The deportive center of the request
     * @param status The status of the request
     * @param totalPrice The total price of the request
     * @param dates List of dates the center is requested
     */
    public RentRequest(User user, DeportiveCenter deportiveCenter, String status, int totalPrice, List<Date> dates)
    {
        this.user = user;
        this.deportiveCenter = deportiveCenter;
        this.status = status;
        this.totalPrice = totalPrice;
        this.dates = dates;
    }

    /**
     * Empty Constructor, initialize String as null and  int as -1
     */
    public RentRequest()
    {
        this.user = null;
        this.deportiveCenter = null;
        this.status = null;
        this.totalPrice = -1;
        this.dates = null;
    }

    /**
     * Gets the ID of the request
     * @return The ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the request
     * @param id The new ID number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user of the request
     * @return The user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user of the request
     * @param user The new user object
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the deportive center of the request
     * @return The deportive center object
     */
    public DeportiveCenter getDeportiveCenter() {
        return deportiveCenter;
    }

    /**
     * Sets the deportive center of the request
     * @param deportiveCenter The new deportive center object
     */
    public void setDeportiveCenter(DeportiveCenter deportiveCenter) {
        this.deportiveCenter = deportiveCenter;
    }

    /**
     * Gets the status of the request
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the request
     * @param status The new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the total price of the request
     * @return The total price
     */
    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the request
     * @param totalPrice The new total price
     */
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the list of dates of the request
     * @return The list of dates
     */
    public List<Date> getDates() {
        return dates;
    }

    /**
     * Sets the list of dates of the request
     * @param dates The new list of dates
     */
    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

}
