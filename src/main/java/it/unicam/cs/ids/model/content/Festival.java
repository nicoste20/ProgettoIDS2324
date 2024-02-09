package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Date;
import java.util.List;

/**
 * Represents a Festival, a type of content that includes a list of points.
 * Extends the base class Itinerary.
 */
@Entity
public class Festival extends Itinerary{
    /** the starting date of a festival. */
    @Column(name="start")
    private  Date start;
    /** the ending date of a festival. */
    @Column(name="end")
    private  Date end;

    /** the description of a festival. */
    private String information;

    /**
     * Constructor to create an itinerary with an author, text description, and a unique identifier.
     *
     * @param author The author of the festival.
     * @param text The unique name of the festival.
     * @param id The unique identifier for the festival.
     * @param points the list of points related to a festival.
     * @param information the description of a festival.
     * @param start  the starting date of a festival.
     * @param end  the ending date of a festival.
     */
    public Festival(BaseUser author, String text, List<Point> points, int id, Date start, Date end, String information) {
        super(author, text, points, id);
        this.start= start;
        this.end=end;
        this.information = information;
    }

    public Festival() {

    }

    /**
     *it gets the starting date of a festival
     * @return the starting date
     */
    public Date getStart(){
        return this.start;
    }
    /**
     *it gets the ending date of a festival
     * @return the ending date
     */
    public Date getEnd(){
        return this.end;
    }

    /**
     * Get festival information
     * @return the festival information
     */
    public String getInformation() {
        return information;
    }
}
