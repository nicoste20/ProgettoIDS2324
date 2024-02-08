package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.IUserPlatform;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an itinerary, a type of content that includes a list of points.
 * Extends the base class Content.
 */
public class Itinerary extends Content {

    /** The list of points in the itinerary. */
    private List<Point> points;

    /**
     * Constructor to create an itinerary with an author, text description, and a unique identifier.
     *
     * @param author The author of the itinerary.
     * @param text The textual description of the itinerary.
     * @param id The unique identifier for the itinerary.
     */
    public Itinerary(IUserPlatform author, String text, int id) {
        super(author, text, id);
        this.points = new ArrayList<>();
    }

    /**
     * Constructor to create an itinerary with an author, text description, a list of points, and a unique identifier.
     *
     * @param author The author of the itinerary.
     * @param text The textual description of the itinerary.
     * @param points The list of points in the itinerary.
     * @param id The unique identifier for the itinerary.
     */
    public Itinerary(IUserPlatform author, String text, List<Point> points, int id) {
        super(author, text, id);
        this.points = points;
    }

    /**
     * Getter for the list of points in the itinerary.
     *
     * @return The list of points in the itinerary.
     */
    public List<Point> getPoints() {
        return this.points;
    }

    /**
     * Adds a point to the itinerary.
     *
     * @param point The point to be added to the itinerary.
     */
    public void addPoint(Point point) {
        this.points.add(point);
    }
}

