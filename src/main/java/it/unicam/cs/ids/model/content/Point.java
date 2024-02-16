package it.unicam.cs.ids.model.content;
import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.*;
import it.unicam.cs.ids.util.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a point, a type of content that includes coordinates and a specific type.
 * Extends the base class Content.
 */
@Entity
public abstract class Point extends Content implements Cloneable{


    /** The type of the point. */
    private String type;

    /** The coordinates of the point in 2D space. */
    @OneToOne
    private Point2D coordinates;

    @OneToMany
    private List<Multimedia> multimediaList;
    /**
     * Constructor to create a point with coordinates, type, author, text description, and a unique identifier.
     *
     * @param point  The coordinates of the point in 2D space.
     * @param type   The type of the point.
     * @param author The author of the point.
     * @param name   The textual description of the point.
     * @param id     The unique identifier for the point.
     */
    public Point(Point2D point, String type, BaseUser author, String name, int id) {
        super(author, name, id);
        this.coordinates = point;
        this.type = type;
        this.multimediaList=new ArrayList<Multimedia>();
    }

    public Point() {
        super(null,null,-1);
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

    public void addMultimedia(Multimedia multimedia){
        this.multimediaList.add(multimedia);
    }

    public List<Multimedia> getMultimediaList(){
        return this.multimediaList;
    }

    @Override
    public abstract Point clone();
}

