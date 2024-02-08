package it.unicam.cs.ids.model.content;
import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.Entity;
import it.unicam.cs.ids.util.Point2D;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a point, a type of content that includes coordinates and a specific type.
 * Extends the base class Content.
 */
@MappedSuperclass
public abstract class Point extends Content implements Cloneable{

    /** The title of this point **/
    private String title;

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
     * @param text   The textual description of the point.
     * @param id     The unique identifier for the point.
     * @param title the title of the point
     */
    public Point(Point2D point, String type, BaseUser author, String text, int id, String title) {
        super(author, text, id);
        this.coordinates = point;
        this.type = type;
        this.title = title;
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

    public String getTitle(){
        return this.title;
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

