package it.unicam.cs.ids.model;

import it.unicam.cs.ids.model.user.BaseUser;

/**
 * The abstract class representing content in the application.
 * This serves as a base class for various types of content.
 */
public abstract class Content {

    /** The author of the content. */
    private final BaseUser author;

    /** The textual description of the content. */
    private String description;

    /** Flag indicating whether the content is validated or not. */
    private boolean isValidate;

    /** The unique identifier for the content. */
    private final int id;

    /**
     * Constructor to initialize a Content object.
     *
     * @param author The author of the content.
     * @param text The textual description of the content.
     * @param id The unique identifier for the content.
     */
    public Content(BaseUser author, String text, int id) {
        this.author = author;
        this.description = text;
        this.id = id;
    }

    /**
     * Getter for the author of the content.
     *
     * @return The author of the content.
     */
    public BaseUser getAuthor() {
        return author;
    }

    /**
     * Getter for the textual description of the content.
     *
     * @return The textual description of the content.
     */
    public String getDescription() {
        return description;
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
        return this.author + " " + this.description;
    }

    /**
     * Getter for the unique identifier of the content.
     *
     * @return The unique identifier of the content.
     */
    public int getId() {
        return this.id;
    }
}


