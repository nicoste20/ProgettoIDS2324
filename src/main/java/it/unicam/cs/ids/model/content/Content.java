package it.unicam.cs.ids.model.content;

import jakarta.persistence.*;

/**
 * The abstract class representing content in the application.
 * This serves as a base class for various types of content.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Content {

    private int author;
    private String name;
    private String description;
    private boolean isValidate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    /**
     * Constructor to initialize a Content object.
     * @param name The name of the content.
     * @param description The detailed description of the content.
     */
    public Content(String name, String description) {
        this.name = name;
        this.isValidate = false;
        this.description = description;
    }

    /**
     * Default constructor.
     */
    public Content() {

    }

    /**
     * Getter for the author of the content.
     * @return The author of the content.
     */
    public int getAuthor() {
        return author;
    }

    /**
     * Setter for the author of the content.
     * @param author The author of the content.
     */
    public void setAuthor(int author){this.author = author;}

    /**
     * Getter for the name of the content.
     * @return The name of the content.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the validation status of the content.
     * @return True if the content is validated, false otherwise.
     */
    public boolean isValidate() {
        return this.isValidate;
    }

    /**
     * Setter for updating the validation status of the content.
     * @param val The new validation status to be set.
     */
    public void setValidation(boolean val) {
        this.isValidate = val;
    }

    /**
     * Getter for the unique identifier of the content.
     * @return The unique identifier of the content.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set a new name for the content
     * @param description the new name
     */
    public void setName(String description){this.name =description;}

    /**
     * Getter for the detailed description of the content.
     * @return the detailed description of the content.
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Setter for the detailed description of the content.
     * @return The new detailed description of the content.
     */
    public void setDescription(String description){
        this.description = description;
    }
}


