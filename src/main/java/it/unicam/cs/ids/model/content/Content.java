package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.*;

/**
 * The abstract class representing content in the application.
 * This serves as a base class for various types of content.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Content {

    /** The author of the content. */

    private int author;

    /** The textual description of the content. */
    private String name;

    /** Flag indicating whether the content is validated or not. */
    private boolean isValidate;

    /** The unique identifier for the content. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    /**
     * Constructor to initialize a Content object.
     *
     * @param text The textual description of the content.
     */
    public Content(String name) {
        this.name = name;
        this.isValidate = false;
    }

    public Content() {

    }

    /**
     * Getter for the author of the content.
     *
     * @return The author of the content.
     */
    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author){this.author = author;}

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
}


