package it.unicam.cs.ids.model.content;

import jakarta.persistence.*;

/**
 *  Represents a base implementation of the {@link Content} interface.
 * This class provides methods that concerns about Comments.
 */
@Entity
public class Comment{

    private boolean isValidate;
    private int authorId;
    private String comment;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param comment   The text of the comment.
     */
    public Comment(String comment) {
        this.isValidate = false;
        this.comment = comment;
    }

    /**
     * Default constructor.
     */
    public Comment() {
        this.isValidate = false;
    }

    /**
     * Retrieves the ID of the author who wrote the comment.
     *
     * @return The author ID.
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Sets the ID of the author who wrote the comment.
     *
     * @param authorId The author ID to set.
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Retrieves the text of the comment.
     *
     * @return The comment text.
     */
    public String getComment() {
        return comment;
    }


    /**
     * Retrieves the ID of the comment.
     *
     * @return The comment ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the comment.
     * @param id The comment ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if the comment is validated.
     * @return True if the comment is validated, false otherwise.
     */
    public boolean isValidate() {
        return isValidate;
    }

    /**
     * Sets the validation status of the comment.
     * @param validate The validation status to set.
     */
    public void setValidation(boolean validate) {
        isValidate = validate;
    }
}