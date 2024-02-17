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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Point implements Cloneable{

    public void setAuthor(int author) {
        this.author = author;
    }

    private int author;

    /** The textual description of the content. */
    @Column(name="name", unique = true)
    private String name;

    /** Flag indicating whether the content is validated or not. */
    @Column(name="isValidate")
    private boolean isValidate;

    /** The unique identifier for the content. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /** The type of the point. */
    @Column(name="type")
    private String type;

    /** The coordinates of the point in 2D space. */
    //@Column(name="x")
    private Float x;
    // @Column(name="y")
    private Float y;
    @OneToMany
    private List<Multimedia> multimediaList;
    /**
     * Constructor to create a point with coordinates, type, author, text description, and a unique identifier.
     *
     * @param type   The type of the point.
     * @param name   The textual description of the point.
     */
    public Point(Float x , Float y, String type, String name) {
        this.name=name;
        this.isValidate=false;
        this.x = x;
        this.y = y;
        this.type = type;
        this.multimediaList=new ArrayList<Multimedia>();
        //new Point2D(x,y);
    }

    public Point() {

    }

    /**
     * Getter for the coordinates of the point.
     *
     * @return The coordinates of the point in 2D space.
     */

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
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
    public int getAuthor() {
        return author;
    }

    /**
     * Getter for the textual description of the content.
     *
     * @return The textual description of the content.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the validation status of the content.
     *
     * @return True if the content is validated, false otherwise.
     */
    public boolean isValidate() {
        return this.isValidate;
    }

    /**
     * Setter for updating the validation status of the content.
     *
     * @param val The new validation status to be set.
     */
    public void setValidation(boolean val) {
        this.isValidate = val;
    }

    /**
     * Returns a string representation of the Content object.
     *
     * @return A string containing the author and description of the content.
     */
    public String toString() {
        return this.author + " " + this.name;
    }

    /**
     * Getter for the unique identifier of the content.
     *
     * @return The unique identifier of the content.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set a new description for the content
     * @param description the new description
     */
    public void setName(String description){this.name =description;}

    @Override
    public abstract Point clone();
}

