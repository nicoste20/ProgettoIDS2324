package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.point.Monument;
import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.lang.management.MonitorInfo;
import java.util.Date;
import java.util.List;

/**
 * Represents a Festival, a type of content that includes a list of points.
 * Extends the base class Itinerary.
 */
@Entity
public class Festival extends Itinerary{
    /** the starting date of a festival. */
    @Column(name="startDate")
    private  Date startDate;
    /** the ending date of a festival. */
    @Column(name="endDate")
    private  Date endDate;

    /**
     * Constructor to create an itinerary with an author, text description, and a unique identifier.
     *
     * @param name The unique name of the festival.
     * @param points the list of points related to a festival.
     * @param description the description of a festival.
     * @param start  the starting date of a festival.
     * @param end  the ending date of a festival.
     */
    public Festival(String name, List<Integer> points, Date start, Date end, String description) {
        super(name, points , description);
        this.startDate = start;
        this.endDate =end;
    }

    public Festival() {
    }

    /**
     *it gets the starting date of a festival
     * @return the starting date
     */
    public Date getStartDate(){
        return this.startDate;
    }
    /**
     *it gets the ending date of a festival
     * @return the ending date
     */
    public Date getEndDate(){
        return this.endDate;
    }

}
