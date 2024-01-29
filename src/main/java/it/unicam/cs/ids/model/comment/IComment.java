package it.unicam.cs.ids.model.comment;

import it.unicam.cs.ids.model.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;

/**
 Interface representing a comment under a multimedia content.
 It defines methods to retrieve basic attributes about a comment.
 */
public interface IComment {
    /**
     * Gets the text of a comment.
     *
     * @return the text of a comment.
     */
    public String getText();
    /**
     * Gets the author of a comment.
     *
     * @return the author of a comment.
     */
    public BaseUser getAuthor();
    /**
     * Gets the content associated with a comment.
     *
     * @return the content associated with a comment.
     */
    public Multimedia getContent();
    /**
     * Gets the validation of a comment.
     *
     * @return if the comment needs validation.
     */
    public boolean commentValidation();

}
