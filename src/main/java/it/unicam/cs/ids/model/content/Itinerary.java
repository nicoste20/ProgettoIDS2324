package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.point.GreenZone;
import it.unicam.cs.ids.model.point.Monument;
import it.unicam.cs.ids.model.point.Restaurant;
import it.unicam.cs.ids.model.point.Square;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an itinerary, a type of content that includes a list of points.
 * Extends the base class Content.
 */
@Entity
public class Itinerary extends Content {

    /** The list of points in the itinerary. */

    @ElementCollection
    @CollectionTable(name = "itinerary_points", joinColumns = @JoinColumn(name = "itinerary_id"))
    @Column(name = "point_id")
    private List<Integer> points;

    /**
     * Constructor to create an itinerary with an author, text description, and a unique identifier.
     *
     * @param text The textual description of the itinerary.
     */


    /**
     * Constructor to create an itinerary with an author, text description, a list of points, and a unique identifier.
     *
     * @param text The textual description of the itinerary.
     * @param points The list of points in the itinerary.
     */
    public Itinerary(String text, List<Integer> points) {
        super(text);
        this.points = points;
    }

    public Itinerary() {
        super(null);
    }

    /**
     * Getter for the list of points in the itinerary.
     *
     * @return The list of points in the itinerary.
     */
    public List<Integer> getPoints() {
        return this.points;
    }

    /**
     * Adds a point to the itinerary.
     *
     * @param point The point to be added to the itinerary.
     */
    public void addPoint(Integer point) {
        this.points.add(point);
    }
}

