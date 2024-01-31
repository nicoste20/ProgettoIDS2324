package it.unicam.cs.ids.model;

import it.unicam.cs.ids.model.user.BaseUser;

import java.awt.geom.Point2D;

/**
 * Represents a point, a type of content that includes coordinates and a specific type.
 * Extends the base class Content.
 */
public class Point extends Content {

    /** The title of this point **/
    private final String title;

    /** The type of the point. */
    private final String type;

    /** The coordinates of the point in 2D space. */
    private Point2D coordinates;

    /**
     * Constructor to create a point with coordinates, type, author, text description, and a unique identifier.
     *
     * @param point  The coordinates of the point in 2D space.
     * @param type   The type of the point.
     * @param author The author of the point.
     * @param text   The textual description of the point.
     * @param id     The unique identifier for the point.
     * @param title
     */
    public Point(Point2D point, String type, BaseUser author, String text, int id, String title) {
        super(author, text, id);
        this.coordinates = point;
        this.type = type;
        this.title = title;
    }

    /**
     * Getter for the coordinates of the point.
     *
     * @return The coordinates of the point in 2D space.
     */
    public Point2D getCoordinates() {
        return this.coordinates;
    }

    /**
     * Getter for the type of the point.
     *
     * @return The type of the point.
     */
    public String getType() {
        return this.type;
    }

    public String getTitle(){
        return this.title;
    }
}

