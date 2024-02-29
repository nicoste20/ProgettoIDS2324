package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.*;
import it.unicam.cs.ids.util.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a point, a type of content that includes coordinates and a specific type.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Point {

    private int author;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "isValidate")
    private boolean isValidate;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "type")
    private String type;
    private Float x;
    private Float y;

    @ElementCollection
    @CollectionTable(name = "point_comments", joinColumns = @JoinColumn(name = "point_id"))
    @Column(name = "comment_id")
    private List<Integer> comments;

    /**
     * Constructor to create a point with coordinates, type, author, text description, and a unique identifier.
     *
     * @param x    the x-coordinate of the point
     * @param y    the y-coordinate of the point
     * @param type the type of the point
     * @param name The textual description of the point.
     */
    public Point(Float x, Float y, String type, String name) {
        this.name = name;
        this.isValidate = false;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Default constructor.
     */
    public Point() {

    }

    /**
     * Getter for the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    public Float getX() {
        return x;
    }

    /**
     * Getter for the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
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

    /**
     * Setter for the author of the point.
     *
     * @param author The author of the point.
     */
    public void setAuthor(int author) {
        this.author = author;
    }

    /**
     * Getter for the author of the point.
     *
     * @return The author of the point.
     */
    public int getAuthor() {
        return author;
    }

    /**
     * Getter for the name of the point.
     *
     * @return The name of the point.
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
     * Setter for the validation status of the content.
     */
    public void setValidation(boolean val) {
        this.isValidate = val;
    }

    /**
     * Getter for the unique identifier of the point.
     *
     * @return The unique identifier of the point.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set a new description for the content
     *
     * @param description the new description
     */
    public void setName(String description) {
        this.name = description;
    }

    /**
     * Adds a comment to the point.
     *
     * @param id The ID of the comment to be added.
     */
    public void addComment(int id) {
        this.comments.add(id);
    }

    /**
     * Retrieves the list of comments associated with the point.
     *
     * @return The list of comments associated with the point.
     */
    public List<Integer> getComments() {
        return this.comments;
    }

    /**
     * Deletes a comment from the point.
     *
     * @param id The ID of the comment to be deleted.
     */
    public void deleteComment(int id) {
        this.comments.remove(id);
    }

    /**
     * Abstract method to create a clone of the Point object.
     *
     * @return A clone of the Point object.
     */
    public abstract Point clone();
}
