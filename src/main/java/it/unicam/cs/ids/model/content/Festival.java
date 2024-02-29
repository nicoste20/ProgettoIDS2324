package it.unicam.cs.ids.model.content;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.Date;
import java.util.List;

/**
 * Represents a Festival, a type of content that includes a list of points for a defined time.
 * Extends the base class Itinerary.
 */
@Entity
public class Festival extends Itinerary{
    @Column(name="startDate")
    private  Date startDate;

    @Column(name="endDate")
    private  Date endDate;

    /**
     * Constructor to create an itinerary with an author, text description, and a unique identifier.
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

    /**
     * Default constructor.
     */
    public Festival() {
    }

    /**
     * It gets the starting date of a festival
     * @return the starting date
     */
    public Date getStartDate(){
        return this.startDate;
    }
    /**
     * It gets the ending date of a festival
     * @return the ending date
     */
    public Date getEndDate(){
        return this.endDate;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

}
