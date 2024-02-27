package it.unicam.cs.ids.model.content;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents an itinerary, a type of content that includes a list of points.
 * Extends the base class Content.
 */
@Entity
public class Itinerary extends Content {
    @ElementCollection
    @CollectionTable(name = "itinerary_points", joinColumns = @JoinColumn(name = "itinerary_id"))
    @Column(name = "point_id")
    private List<Integer> points;

    @ElementCollection
    @CollectionTable(name = "itinerary_comments", joinColumns = @JoinColumn(name = "itinerary_id"))
    @Column(name = "comment_id")
    private List<Integer> comments;

    /**
     * Constructor to create an itinerary with a name, a list of points, and a description.
     * @param name        The textual description of the itinerary.
     * @param points      The list of points in the itinerary.
     * @param description The textual description of the itinerary.
     */
    public Itinerary(String name, List<Integer> points , String description) {
        super(name,description);
        this.points = points;
    }

    /**
     * Default constructor.
     */
    public Itinerary() {
        super(null, "");
    }

    /**
     * Getter for the list of points in the itinerary.
     * @return The list of points in the itinerary.
     */
    public List<Integer> getPoints() {
        return this.points;
    }

    /**
     * Adds a point to the itinerary.
     * @param point The point to be added to the itinerary.
     */
    public void addPoint(Integer point) {
        this.points.add(point);
    }

    public void addComment(int id){this.comments.add(id);}

    public List<Integer> getComments(){return this.comments;}

    public void deleteComment(int id){this.comments.remove(id);}
}

