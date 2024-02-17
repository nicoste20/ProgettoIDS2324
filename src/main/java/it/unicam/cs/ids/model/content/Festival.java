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

    /** the description of a festival. */
    private String information;

    /**
     * Constructor to create an itinerary with an author, text description, and a unique identifier.
     *
     * @param text The unique name of the festival.
     * @param points the list of points related to a festival.
     * @param information the description of a festival.
     * @param start  the starting date of a festival.
     * @param end  the ending date of a festival.
     */
    public Festival(String text, List<Integer> points, Date start, Date end, String information) {
        super(text, points);
        this.startDate = start;
        this.endDate =end;
        this.information = information;
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

    /**
     * Get festival information
     * @return the festival information
     */
    public String getInformation() {
        return information;
    }
}
