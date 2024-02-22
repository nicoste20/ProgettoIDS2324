package it.unicam.cs.ids.model.point;

import it.unicam.cs.ids.model.content.Point;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Date;
/**
 * This class represents a monument, which is a type of point on a map that signifies a significant historical or cultural site.
 * It extends the Point class and adds fields to store the inauguration date and a brief story or description of the monument.
 */
@Entity
public class Monument extends Point {
    @Column(name="inaugurationDate")
    private Date inaugurationDate;
    @Column(name="story")
    private String story;

    /**
     * Constructs a Monument object with the specified coordinates, type, name, inauguration date, and story.
     * @param x the x-coordinate of the monument
     * @param y the y-coordinate of the monument
     * @param type the type of the monument
     * @param name the name of the monument
     * @param inaugurationDate the inauguration date of the monument
     * @param story a brief story or description of the monument
     */
    public Monument(Float x, Float y, String type, String name, Date inaugurationDate, String story) {
        super(x, y,type , name);
        this.inaugurationDate = inaugurationDate;
        this.story = story;
    }

    /**
     * Default constructor for Monument class.
     */
    public Monument() {
    }

    /**
     * Retrieves the inauguration date of the monument.
     * @return the inauguration date of the monument
     */
    public Date getInaugurationDate() {
        return inaugurationDate;
    }

    /**
     * Retrieves the story or description of the monument.
     * @return the story or description of the monument
     */
    public String getStory() {
        return story;
    }

    /**
     * Creates and returns a copy of this Monument object.
     * @return a clone of this Monument object
     */
    @Override
    public Monument clone() {
        return new Monument(this.getX(), this.getY(),super.getName(), super.getType(), this.getInaugurationDate(), this.getStory());
    }
}
