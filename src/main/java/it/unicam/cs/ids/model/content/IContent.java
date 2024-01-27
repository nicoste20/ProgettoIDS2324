package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.comment.Comment;
import it.unicam.cs.ids.model.comment.IComment;
import it.unicam.cs.ids.model.user.IUserPlatform;

import java.util.List;

/**
 * The  IContent interface represents content with user information, a photo,
 * a description, and the ability to handle comments.
 * Implementations of this interface should provide methods to access and manipulate these attributes.
 */
public interface IContent {

    /**
     * Gets the user associated with the content.
     *
     * @return the user associated with the content
     */
    IUserPlatform getUser();

    /**
     * Gets the photo of the content.
     *
     * @return the photo of the content
     */
    String getFile();

    /**
     * Adds a comment to the content.
     *
     * @param comment the comment to be added
     */
    void addComment(IComment comment);

    /**
     * Gets the list of comments associated with the content.
     *
     * @return the list of comments
     */
    List <Comment> getComment();

    /**
     * Gets the description of the content.
     *
     * @return the description of the content
     */
    String getDescription();
}
